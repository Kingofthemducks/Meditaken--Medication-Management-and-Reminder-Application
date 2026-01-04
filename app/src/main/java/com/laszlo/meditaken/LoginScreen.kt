import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.laszlo.meditaken.ui.theme.MeditakenTheme

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val sharedPrefs = context.getSharedPreferences("MeditakenPrefs", Context.MODE_PRIVATE)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))


        Button(onClick = {

            sharedPrefs.edit().putBoolean("isLoggedIn", true).apply()


            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Sign In")
        }

        Spacer(modifier = Modifier.height(8.dp))


        TextButton(
            onClick = { navController.navigate("signup") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Account")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MeditakenTheme {
        LoginScreen(navController = rememberNavController())
    }
}
