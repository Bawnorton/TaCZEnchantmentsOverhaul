import dev.kikugie.fletching_table.mixin.MixinEnvironment
import dev.kikugie.stonecutter.controller.task.StonecutterTaskMutexService

plugins {
    `maven-publish`
    id("net.neoforged.moddev.legacyforge")
    id("me.modmuss50.mod-publish-plugin")
    id("dev.isxander.secrets")
    alias(ft.plugins.mixin)
}

val loader = "forge"
version = "${sc.properties["mod.version"]}+${sc.current.version}-$loader"
base.archivesName = sc.properties["mod.id"]

repositories {
    maven("https://maven.minecraftforge.net")
    maven("https://maven.bawnorton.com/releases")
    maven("https://maven.parchmentmc.org")
    maven("https://thedarkcolour.github.io/KotlinForForge/")
    maven("https://maven.isxander.dev/releases")
}

dependencies {
    modImplementation("curse.maven:timeless-and-classic-zero-1028108:${sc.properties["deps.tacz"]}")
    modImplementation("dev.isxander:yet-another-config-lib:${sc.properties["deps.yacl"]}-$loader")

    modImplementation("maven.modrinth:tacz-tweaks:${sc.properties["deps.tacz_tweaks"]}")
    modRuntimeOnly("thedarkcolour:kotlinforforge:${sc.properties["deps.kotlinforforge"]}")

    modCompileOnly("curse.maven:tacz-durability-1065328:${sc.properties["deps.tacz_durability"]}")

    modCompileOnly("maven.modrinth:shots-fired:${sc.current.version}-0.2.4.1")

    compileOnly(annotationProcessor("io.github.llamalad7:mixinextras-common:${sc.properties["deps.mixinextras"]}")!!)
    implementation(jarJar("io.github.llamalad7:mixinextras-$loader:${sc.properties["deps.mixinextras"]}")!!)

    implementation(annotationProcessor("com.github.bawnorton.mixinsquared:mixinsquared-common:${sc.properties["deps.mixinsquared"]}")!!)
    jarJar(implementation("com.github.bawnorton.mixinsquared:mixinsquared-$loader:${sc.properties["deps.mixinsquared"]}")!!)

    annotationProcessor("org.spongepowered:mixin:${sc.properties["deps.mixin"]}:processor")
}

val requiredJava = JavaVersion.VERSION_17

java {
    withSourcesJar()
    targetCompatibility = requiredJava
    sourceCompatibility = requiredJava

    toolchain {
        vendor = JvmVendorSpec.ADOPTIUM
        languageVersion = JavaLanguageVersion.of(requiredJava.majorVersion)
    }
}

legacyForge {
    enable {
        forgeVersion = sc.properties["deps.forge_loader"]
    }

    mods {
        register(sc.properties["mod.id"]) {
            sourceSet(sourceSets["main"])
        }
    }

    parchment {
        val (mc, version) = sc.properties["deps.parchment"].split(':')
        mappingsVersion = version
        minecraftVersion = mc
    }

    runs {
        all {
            gameDirectory = rootProject.file("run")
            systemProperty("terminal.ansi", "true")
        }

        register("client") {
            ideName = "Forge Client ${sc.current.version}"
            client()

            programArgument("--username=Bawnorton")
            programArgument("--uuid=17c06cab-bf05-4ade-a8d6-ed14aaf70545")
        }

        register("server") {
            ideName = "Forge Server ${sc.current.version}"
            server()
        }
    }
}

mixin {
    add(sourceSets.main.get(), "${sc.properties["mod.id"]}.refmap.json")
    config("${sc.properties["mod.id"]}.mixin.json")
    config("${sc.properties["mod.id"]}.client.mixin.json")
}


fletchingTable {
    mixins.configure(sourceSets.main) {
        mixin("${sc.properties["mod.id"]}.mixin.json")
        mixin("${sc.properties["mod.id"]}.client.mixin.json", "client") {
            environment = MixinEnvironment.Env.CLIENT
        }
    }
}

stonecutter {
    replacements.string(constants[loader] == true) {
        replace("net.neoforged", "net.minecraftforge")
        replace(".bus.", ".eventbus.")
    }

    files.disable(
        !current.parsed.eq("1.20.1"),
        "**/v1_20_1/"
    )

    files.disable(
        !current.parsed.eq("1.21.1"),
        "**/v1_21_1/"
    )
}

tasks {
    processResources {
        fun MutableMap<String, String>.register(key: String, property: String) {
            val value: String = sc.properties[property]
            inputs.property(key, value)
            set(key, value)
        }

        val props = buildMap {
            register("id", "mod.id")
            register("name", "mod.name")
            register("version", "mod.version")
            register("minecraft", "mod.mc_compat")
            register("description", "mod.description")
            register("license", "mod.license")
        }

        filesMatching(listOf("META-INF/mods.toml", "pack.mcmeta")) {
            expand(props)
        }

        val mixinJava = "JAVA_${requiredJava.majorVersion}"

        filesMatching("*.mixin.json") {
            expand("java" to mixinJava)
        }

        exclude("META-INF/neoforge.mods.toml")
    }

    named("createMinecraftArtifacts") {
        dependsOn("stonecutterGenerate")

        usesService(StonecutterTaskMutexService.create(gradle, name))
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        description = "Builds mod jars and copies results to `build/libs/{mod version}/`"

        inputs.property("version", sc.properties["mod.version"])
        from(named<Jar>("reobfJar").flatMap { it.archiveFile }, named<Jar>("sourcesJar").flatMap { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs/${sc.properties["mod.version"]}"))
    }

    named<Jar>("jar") {
        manifest.attributes("MixinConfigs" to listOf(
            "${sc.properties["mod.id"]}.mixin.json",
            "${sc.properties["mod.id"]}.client.mixin.json"
        ).joinToString(", "))
    }

    withType<AbstractPublishToMaven>().configureEach {
        usesService(StonecutterTaskMutexService.create(gradle, name))
    }
}

val isPublishing = gradle.startParameter.taskNames.any {
    it.contains("publish", ignoreCase = true)
}

extensions.configure<PublishingExtension> {
    repositories {
        maven {
            name = "bawnorton"
            url = uri("https://maven.bawnorton.com/releases")
            if (isPublishing) {
                credentials {
                    username = onePassword["op://Private/Maven API Key/username"].get()
                    password = onePassword["op://Private/Maven API Key/credential"].get()
                }
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "${sc.properties["mod.group"]}.${sc.properties["mod.id"]}"
            artifactId = "${sc.properties["mod.id"]}-$loader"

            from(components["java"])
        }
    }
}

publishMods {
    val mrTokenProvider = onePassword["op://Private/Modrinth API Key/credential"]
    val cfTokenProvider = onePassword["op://Private/Curseforge API Key/credential"]

    type = STABLE
    file = tasks.named<Jar>("reobfJar").map { it.archiveFile.get() }
    additionalFiles.from(tasks.named<Jar>("sourcesJar").map { it.archiveFile.get() })

    displayName = "${sc.properties["mod.name"]} Forge ${sc.properties["mod.version"]} for ${sc.current.version}"
    changelog = provider { rootProject.file("CHANGELOG.md").readText() }
    modLoaders.add(loader)

    val compatibleVersions: List<String> = sc.properties.rawOrNull("mod", "mc_releases")
        ?.asList().orEmpty().map { it.toString() }

    modrinth {
        projectId = sc.properties["publishing.modrinth"]
        accessToken = mrTokenProvider
        minecraftVersions.addAll(compatibleVersions)
        requires("timeless-and-classics-zero")
    }

    curseforge {
        projectId = sc.properties["publishing.curseforge"]
        accessToken = cfTokenProvider
        minecraftVersions.addAll(compatibleVersions)
        server.set(true)
        client.set(true)
        requires("timeless-and-classics-zero")
    }
}