package com.partyremote.android.execution_handling

import com.partyremote.android.ui.components.DialogProvider
import sk.backbone.android.shared.execution.BaseExecutor
import sk.backbone.android.shared.execution.ExecutorParams
import sk.backbone.android.shared.execution.IExecutorDialogProvider
import sk.backbone.android.shared.repositories.server.client.exceptions.IExceptionDescriptionProvider

open class Executor<T>(executorParams: ExecutorParams): BaseExecutor<T>(executorParams)  {
    override val dialogProvider: IExecutorDialogProvider get() = DialogProvider()
    override val exceptionDescriptionProvider: IExceptionDescriptionProvider get() = ExceptionDescriptionProvider()
    override val logToFirebase: Boolean = true

}