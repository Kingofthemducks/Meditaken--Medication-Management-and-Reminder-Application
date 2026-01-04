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
fun SignUpScreen(navController: NavController) {
    val context = LocalContext.current
    val sharedPrefs = context.getSharedPreferences("MeditakenPrefs", Context.MODE_PRIVATE)

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Sign Up",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        // SIGNUP BUTTON
        Button(onClick = {
            // Mark user as logged in
            sharedPrefs.edit().putBoolean("isLoggedIn", true).apply()

            // Navigate to home and clear signup from backstack
            navController.navigate("home") {
                popUpTo("signup") { inclusive = true }
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Sign Up")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // BACK TO LOGIN
        TextButton(
            onClick = { navController.navigate("login") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Already have an account? Sign In")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    MeditakenTheme {
        SignUpScreen(navController = rememberNavController())
    }
}
