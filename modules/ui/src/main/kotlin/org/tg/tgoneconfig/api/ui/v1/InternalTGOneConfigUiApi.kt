package org.tg.tgoneconfig.api.ui.v1

@RequiresOptIn(
    message = "This is TGOneConfig's internal UI plumbing and can change in any release. Use the public kit instead.",
    level = RequiresOptIn.Level.ERROR,
)
@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.TYPEALIAS,
    AnnotationTarget.PROPERTY_SETTER,
)
public annotation class InternalTGOneConfigUiApi
