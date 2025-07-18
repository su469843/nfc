package io.qzz.studyhard.nfc

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.util.Log
import kotlinx.coroutines.*

class AutomationAccessibilityService : AccessibilityService() {

    private val TAG = "AutomationService"
    private var currentTask: Task? = null

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event?.let {
            if (it.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                Log.d(TAG, "Window changed: ${it.packageName}")
                executeNextAction()
            }
        }
    }

    override fun onInterrupt() {}

    fun startTask(task: Task) {
        currentTask = task
        executeNextAction()
    }

    private fun executeNextAction() {
        currentTask?.let { task ->
            if (task.actions.isNotEmpty()) {
                val action = task.actions.removeAt(0)
                when (action.type) {
                    "OPEN_APP" -> openApp(action.data)
                    "CLICK" -> clickView(action.data)
                    // 添加更多动作类型
                }
            } else {
                currentTask = null
            }
        }
    }

    private fun openApp(packageName: String) {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        intent?.let { startActivity(it) }
    }

    private fun clickView(viewId: String) {
        val node = findNodeById(rootInActiveWindow, viewId)
        node?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
    }

    private fun findNodeById(node: AccessibilityNodeInfo?, id: String): AccessibilityNodeInfo? {
        return node?.findAccessibilityNodeInfosByViewId(id)?.firstOrNull()
    }
}

data class Task(val id: Int, val actions: MutableList<Action>)
data class Action(val type: String, val data: String)