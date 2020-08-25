package com.partyremote.android.execution_handling

import com.partyremote.android.ui.components.DialogProvider
import sk.backbone.parent.execution.ExecutorParams
import sk.backbone.parent.execution.IExecutorDialogProvider
import sk.backbone.parent.execution.ParentExecutor
import sk.backbone.parent.repositories.server.client.exceptions.IExceptionDescriptionProvider

open class Executor<T>(executorParams: ExecutorParams): ParentExecutor<T>(executorParams)  {
    override val dialogProvider: IExecutorDialogProvider get() = DialogProvider()
    override val exceptionDescriptionProvider: IExceptionDescriptionProvider get() = ExceptionDescriptionProvider()
    override val logToFirebase: Boolean = true

}