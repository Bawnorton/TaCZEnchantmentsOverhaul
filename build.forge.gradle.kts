import tacz_eo.utils.*

plugins {
  kotlin("jvm")
  `maven-publish`
  id("net.neoforged.moddev.legacyforge")
  id("tacz_eo.common")
  id("me.modmuss50.mod-publish-plugin")
}

repositories {
  fun strictMaven(url: String, alias: String, vararg groups: String) = exclusiveContent {
    forRepository { maven(url) { name = alias } }
    filter { groups.forEach(::includeGroup) }
  }

  maven("https://maven.minecraftforge.net")
  maven("https://maven.bawnorton.com/releases")
  maven("https://maven.parchmentmc.org")
  maven("https://thedarkcolour.github.io/KotlinForForge/")
  maven("https://maven.isxander.dev/releases")

  strictMaven("https://www.cursemaven.com", "Curseforge", "curse.maven")
  strictMaven("https://api.modrinth.com/maven", "Modrinth", "maven.modrinth")
}

val minecraft: String by project
val loader: String by project

base.archivesName = "${mod("id")}-${mod("version")}+$minecraft-$loader"

dependencies {
  modImplementation("curse.maven:timeless-and-classic-zero-1028108:7401617-sources-7401617")
  modImplementation("dev.isxander:yet-another-config-lib:3.6.1+1.20.1-forge")

  modImplementation("maven.modrinth:tacz-tweaks:2.12.2")
  modRuntimeOnly("thedarkcolour:kotlinforforge:4.11.0")

  modImplementation("curse.maven:tacz-durability-1065328:7389190")

  modImplementation("curse.maven:tacz-additions-1356005:7085167")

  implementation(annotationProcessor("io.github.llamalad7:mixinextras-common:0.5.3")!!)
  jarJar(implementation("io.github.llamalad7:mixinextras-forge:0.5.3")!!)

  implementation(annotationProcessor("com.github.bawnorton.mixinsquared:mixinsquared-common:0.3.7-beta.1")!!)
  jarJar(implementation("com.github.bawnorton.mixinsquared:mixinsquared-forge:0.3.7-beta.1")!!)

  annotationProcessor("org.spongepowered:mixin:0.8.5:processor")
}

java {
  withSourcesJar()
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

legacyForge {
  version = deps("forge")

  validateAccessTransformers = true
  accessTransformers.from(rootProject.file("src/main/resources/$minecraft-accesstransformer.cfg"))

  mods {
    register(mod("id")!!) {
      sourceSet(sourceSets["main"])
    }
  }

  deps("parchment") {
    parchment {
      val (mc, version) = it.split(':')
      mappingsVersion = version
      minecraftVersion = mc
    }
  }

  runs {
    all {
      gameDirectory = rootProject.file("run")
    }

    register("client") {
      ideName = "Forge Client $minecraft"
      client()

      programArgument("--username=Bawnorton")
      programArgument("--uuid=17c06cab-bf05-4ade-a8d6-ed14aaf70545")

      systemProperty("terminal.ansi", "true")
    }

    register("server") {
      ideName = "Forge Server $minecraft"
      server()

      systemProperty("terminal.ansi", "true")
    }
  }

  afterEvaluate {
    runs.configureEach {
      applyMixinDebugSettings(::jvmArgument, ::systemProperty)
    }
  }
}

mixin {
  add(sourceSets.main.get(), "${mod("id")}.refmap.json")
  config("${mod("id")}.mixin.json")
  config("${mod("id")}.client.mixin.json")
}

sourceSets.main {
  resources.srcDirs(project.file("src/main/generated/server"), project.file("src/main/generated/client"))
  resources.exclude(".cache")
}

tasks {
  named("createMinecraftArtifacts") {
    dependsOn("stonecutterGenerate")
  }

  register<Copy>("buildAndCollect") {
    group = "build"
    from(named<Jar>("reobfJar").map { it.archiveFile })
    into(rootProject.layout.buildDirectory.file("libs/${mod("version")}"))
    dependsOn("build")
  }

  named<Jar>("jar") {
    manifest {
      attributes(
        "Specification-Title" to mod("name")!!,
        "Specification-Vendor" to "${mod("author")}",
        "Specification-Version" to "1",
        "Implementation-Title" to project.name,
        "Implementation-Version" to mod("version")!!,
        "Implementation-Vendor" to "${mod("author")}",
        "MixinConfigs" to listOf(
          "${mod("id")}.mixin.json",
          "${mod("id")}.client.mixin.json"
        ).joinToString(", ")
      )
    }
  }
}

extensions.configure<PublishingExtension> {
  repositories {
    maven {
      name = "bawnorton"
      url = uri("https://maven.bawnorton.com/releases")
      credentials(PasswordCredentials::class)
      authentication {
        create<BasicAuthentication>("basic")
      }
    }
  }
  publications {
    create<MavenPublication>("maven") {
      groupId = "${mod("group")}.${mod("id")}"
      artifactId = "${mod("id")}-$loader"
      version = "${mod("version")}+$minecraft"

      from(components["java"])
    }
  }
}

publishMods {
  val mrToken = providers.gradleProperty("MODRINTH_TOKEN")
  val cfToken = providers.gradleProperty("CURSEFORGE_TOKEN")

  type = BETA
  file = tasks.named<Jar>("reobfJar").map { it.archiveFile.get() }
  additionalFiles.from(tasks.named<org.gradle.jvm.tasks.Jar>("sourcesJar").map { it.archiveFile.get() })

  displayName = "${mod("name")} Forge ${mod("version")} for $minecraft"
  version = mod("version")
  changelog = provider { rootProject.file("CHANGELOG.md").readText() }
  modLoaders.add(loader)

  val compatibleVersionString = mod("compatible_versions")!!
  val compatibleVersions = compatibleVersionString.split(",").map { it.trim() }

  modrinth {
    projectId = property("publishing.modrinth") as String
    accessToken = mrToken
    minecraftVersions.addAll(compatibleVersions)
  }

  curseforge {
    projectId = property("publishing.curseforge") as String
    accessToken = cfToken
    minecraftVersions.addAll(compatibleVersions)
  }
}