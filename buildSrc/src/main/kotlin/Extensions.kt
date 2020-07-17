import com.android.build.gradle.BaseExtension
import com.android.build.gradle.api.AndroidSourceSet
import com.android.build.gradle.internal.dsl.BuildType
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun org.gradle.api.Project.android(action: BaseExtension.() -> Unit) {
    val extension = extensions.getByName("android") as? BaseExtension
    if (extension != null) action.invoke(extension)
}

fun NamedDomainObjectContainer<BuildType>.release(body: BuildType.() -> Unit) {
    getByName("release") { body(this) }
}

fun org.gradle.api.Project.kotlinCompile(f: KotlinCompile.() -> Unit) {
    this.tasks.withType<KotlinCompile> { f(this) }
}

val NamedDomainObjectContainer<AndroidSourceSet>.main: AndroidSourceSet get() = getByName("main")

val NamedDomainObjectContainer<AndroidSourceSet>.test: AndroidSourceSet get() = getByName("test")

val NamedDomainObjectContainer<AndroidSourceSet>.androidTest: AndroidSourceSet get() = getByName("androidTest")
