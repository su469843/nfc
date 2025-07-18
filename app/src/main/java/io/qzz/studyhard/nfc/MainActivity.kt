package io.qzz.studyhard.nfc
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.net.URI

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainScreen(viewModel = hiltViewModel())
                }
            }
        }
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (intent?.action == NfcAdapter.ACTION_NDEF_DISCOVERED) {
            val rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            rawMsgs?.let {
                val msg = it[0] as NdefMessage
                val payload = String(msg.records[0].payload)
                val uri = URI(payload)
                val taskId = uri.queryParameterNames.firstOrNull { it == "taskid" }?.let { uri.getQueryParameter(it) }?.toIntOrNull()
                taskId?.let { executeTask(it) }
            }
        }
    }

    private fun executeTask(taskId: Int) {
        // 获取任务并执行，使用ViewModel或服务
        Log.d("MainActivity", "Executing task $taskId")
        // 示例: val service = AutomationAccessibilityService.instance
        // service?.startTask(getTask(taskId))
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel) {
    var taskId by remember { mutableStateOf(0) }
    Column {
        Text("NFC Automation App")
        Button(onClick = { /* 创建任务 */ }) { Text("Create Task") }
        Button(onClick = { /* 写入NFC */ }) { Text("Write to NFC") }
    }
}
// 添加更多Compose组件和逻辑