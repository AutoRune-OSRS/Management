package io.autorune.updater.analyzer.classes.annotations

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class CorrectMethodCount(val value: Int)
