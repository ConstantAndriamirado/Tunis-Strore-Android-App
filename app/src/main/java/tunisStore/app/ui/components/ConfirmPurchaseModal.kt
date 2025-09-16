package tunisStore.app.ui.components

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tunisStore.app.CreationCompte2Activity
import tunisStore.app.ui.data.AppData
import tunisStore.app.ui.theme.OrangePrimary

@Composable
fun ConfirmPurchaseModal(
    app: AppData,
    onClose: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    val context = LocalContext.current
    var selectedPaymentMethod by remember { mutableStateOf("Solde Opérateur") }
    val isPaymentMethodValid = selectedPaymentMethod.isNotEmpty() && !selectedPaymentMethod.contains("Prochainement")
    var phoneNumber by remember { mutableStateOf(TextFieldValue("")) }

    val phoneRegex = Regex("^(2|5|9|4|3|7)[0-9]{7}$")

    var telErreur by remember { mutableStateOf(false) }

    val formattedTel = buildString {
        append("+216 ")
        val digitsOnly = phoneNumber.text.filter { it.isDigit() }.take(8)
        when (digitsOnly.length) {
            in 1..2 -> append(digitsOnly)
            in 3..5 -> append(digitsOnly.substring(0, 2)).append(" ").append(digitsOnly.substring(2))
            in 6..8 -> append(digitsOnly.substring(0, 2)).append(" ")
                .append(digitsOnly.substring(2, 5)).append(" ")
                .append(digitsOnly.substring(5))
        }
    }

    val paymentMethods = listOf(
        "Solde Opérateur",
        "Flouci",
        "D17",
        "Carte Bancaire (Prochainement)"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f))
            .clickable(onClick = onClose)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White, RoundedCornerShape(24.dp))
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onClose) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Fermer", tint = Color.Black)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Confirmer l'achat",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // App info
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = app.thumbnailRes),
                    contentDescription = app.name,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = app.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "4.7 ★",
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Développeur : ${app.developer ?: "Inconnu"}",
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Taille : ${app.size}",
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Prix : ${app.price} DT",
                        color = OrangePrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Paiement sécurisé 🔒",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Payment methods
            Text(
                text = "Choisir un mode de paiement",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            paymentMethods.forEach { method ->
                val isAvailable = !method.contains("Prochainement")
                val isSelected = selectedPaymentMethod == method
                val backgroundColor = if (isSelected) Color(0xFFFFEEE9) else Color(0xFFF5F5F5)
                val borderColor = if (isSelected) OrangePrimary else Color.Transparent

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .background(backgroundColor, RoundedCornerShape(12.dp))
                        .border(1.dp, borderColor, RoundedCornerShape(12.dp))
                        .clickable(enabled = isAvailable) { selectedPaymentMethod = method }
                        .padding(12.dp)
                ) {
                    Text(
                        text = method,
                        color = if (isAvailable) Color.Black else Color.Gray,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // User Info
            Text(
                text = "Vos informations",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = TextFieldValue(formattedTel, selection = TextRange(formattedTel.length)),
                onValueChange = { newText ->
                    val digitsOnly = newText.text.removePrefix("+216").replace(" ", "").filter { it.isDigit() }.take(8)
                    phoneNumber = TextFieldValue(digitsOnly, TextRange(digitsOnly.length))
                },
                label = { Text("Numéro de téléphone") },
                placeholder = { Text("+216 71 900 868") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            if (telErreur) {
                Text("Numéro tunisien invalide (8 chiffres valides après +216)", color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Confirm Button
            Button(
                onClick = {
                    telErreur = !phoneNumber.text.matches(phoneRegex)

                    val toutEstValide = !telErreur && isPaymentMethodValid

                    if (toutEstValide) {
                        onConfirm(phoneNumber.text, selectedPaymentMethod)
                        Toast.makeText(context, "Traitement de l'achat...", Toast.LENGTH_SHORT).show()
                    } else {
                        val errorMessage = when {
                            telErreur -> "Veuillez entrer un numéro tunisien valide (8 chiffres)"
                            !isPaymentMethodValid -> "Veuillez sélectionner un mode de paiement valide"
                            else -> "Veuillez vérifier vos informations"
                        }
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    }


                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary)
            ) {
                Text(
                    text = "Continuer",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}