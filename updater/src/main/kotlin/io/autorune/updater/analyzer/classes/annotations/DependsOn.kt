package io.autorune.updater.analyzer.classes.annotations

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import kotlin.reflect.KClass

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class DependsOn(vararg val value: KClass<out ClassAnalyzer>)
