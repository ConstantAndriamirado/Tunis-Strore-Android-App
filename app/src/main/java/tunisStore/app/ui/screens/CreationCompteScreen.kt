package tunisStore.app.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tunisStore.app.Bienvenue
import tunisStore.app.CreationCompte2Activity
import tunisStore.app.R
import tunisStore.app.ui.theme.OrangePrimary
import java.util.Calendar
import kotlin.math.min

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreationCompteScreen() {
    val context = LocalContext.current

    var nom by remember { mutableStateOf("") }
    var prenom by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var tel by remember { mutableStateOf(TextFieldValue("")) }
    var mdp by remember { mutableStateOf("") }
    var mdpVisible by remember { mutableStateOf(false) }
    var confirmMdp by remember { mutableStateOf("") }
    var confirmMdpVisible by remember { mutableStateOf(false) }
    var confirmFocus by remember { mutableStateOf(false) }
    var dateNaissance by remember { mutableStateOf(TextFieldValue("")) }
    var accepterConditions by remember { mutableStateOf(false) }

    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
    val phoneRegex = Regex("^(2|5|9|4|3|7)[0-9]{7}$")
    val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    val dateRegex = Regex("""^\d{2} / \d{2} / \d{4}$""")

    val currentYear = Calendar.getInstance().get(Calendar.YEAR)

    var nomErreur by remember { mutableStateOf(false) }
    var prenomErreur by remember { mutableStateOf(false) }
    var emailErreur by remember { mutableStateOf(false) }
    var telErreur by remember { mutableStateOf(false) }
    var mdpErreur by remember { mutableStateOf(false) }
    var confirmErreur by remember { mutableStateOf(false) }
    var dateErreur by remember { mutableStateOf(false) }
    var cguErreur by remember { mutableStateOf(false) }

    fun isValidDate(dateStr: String): Boolean {
        if (!dateStr.matches(dateRegex)) return false

        return try {
            val parts = dateStr.split("/").map { it.trim() }
            val day = parts[0].toInt()
            val month = parts[1].toInt()
            val year = parts[2].toInt()

            // Vérifier les plages de base
            if (day < 1 || day > 31 || month < 1 || month > 12 || year < 1800 || year > currentYear) {
                return false
            }

            // Vérifier les jours valides par mois
            val maxDays = when (month) {
                4, 6, 9, 11 -> 30
                2 -> if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 29 else 28
                else -> 31
            }

            day <= maxDays
        } catch (e: Exception) {
            false
        }
    }

    val formattedTel = buildString {
        append("+216 ")
        val digitsOnly = tel.text.filter { it.isDigit() }.take(8)
        when (digitsOnly.length) {
            in 1..2 -> append(digitsOnly)
            in 3..5 -> append(digitsOnly.substring(0, 2)).append(" ").append(digitsOnly.substring(2))
            in 6..8 -> append(digitsOnly.substring(0, 2)).append(" ")
                .append(digitsOnly.substring(2, 5)).append(" ")
                .append(digitsOnly.substring(5))
        }
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(
                        color = OrangePrimary,
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                    )
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable {
                                context.startActivity(Intent(context, Bienvenue::class.java))
                            }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Retour",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Retour",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "Créer un compte",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color.White)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = nom,
                onValueChange = { nom = it },
                label = { Text("Nom") },
                placeholder = { Text("REDIDA") },
                modifier = Modifier.fillMaxWidth()
            )
            if (nomErreur) {
                Text("Nom requis", color = Color.Red, fontSize = 12.sp)
            }

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = prenom,
                onValueChange = { prenom = it },
                label = { Text("Prénom") },
                placeholder = { Text("Léo Carlos") },
                modifier = Modifier.fillMaxWidth()
            )
            if (prenomErreur) {
                Text("Prénom requis", color = Color.Red, fontSize = 12.sp)
            }

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Adresse e-mail") },
                placeholder = { Text("carlosredida557@gmail.com") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )
            if (emailErreur) {
                Text("Adresse e-mail invalide", color = Color.Red, fontSize = 12.sp)
            }

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = TextFieldValue(formattedTel, selection = TextRange(formattedTel.length)),
                onValueChange = { newText ->
                    val digitsOnly = newText.text.removePrefix("+216").replace(" ", "").filter { it.isDigit() }.take(8)
                    tel = TextFieldValue(digitsOnly, TextRange(digitsOnly.length))
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

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = mdp,
                onValueChange = { mdp = it },
                label = { Text("Créer un mot de passe") },
                placeholder = { Text("********") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (mdpVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { mdpVisible = !mdpVisible }) {
                        Icon(
                            painter = painterResource(if (mdpVisible) R.drawable.ic_sleepy_eye else R.drawable.ic_eye_closed),
                            contentDescription = "Toggle password",
                            tint = Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            if (mdpErreur) {
                Text("Mot de passe faible (min 8 caractères, 1 chiffre)", color = Color.Red, fontSize = 12.sp)
            }

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = confirmMdp,
                onValueChange = { confirmMdp = it },
                label = { Text("Confirmer le mot de passe") },
                placeholder = { Text("********") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (confirmMdpVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { confirmMdpVisible = !confirmMdpVisible }) {
                        Icon(
                            painter = painterResource(if (confirmMdpVisible) R.drawable.ic_sleepy_eye else R.drawable.ic_eye_closed),
                            contentDescription = "Toggle confirm password",
                            tint = Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { confirmFocus = it.isFocused },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Blue,
                    unfocusedIndicatorColor = Color.Gray
                )
            )
            if (confirmErreur) {
                Text("Les mots de passe ne correspondent pas", color = Color.Red, fontSize = 12.sp)
            }

            Spacer(Modifier.height(8.dp))

            var showDatePicker by remember { mutableStateOf(false) }

            OutlinedTextField(
                value = TextFieldValue(dateNaissance.text, selection = TextRange(dateNaissance.text.length)),
                onValueChange = { newText ->
                    val digitsOnly = newText.text.filter { it.isDigit() }.take(8) // Accepter jusqu'à 8 chiffres pour JJ/MM/AAAA
                    val formatted = buildString {
                        if (digitsOnly.length > 0) {
                            append(digitsOnly.substring(0, min(digitsOnly.length, 2))) // Jour
                        }
                        if (digitsOnly.length > 2) {
                            append(" / ")
                            append(digitsOnly.substring(2, min(digitsOnly.length, 4))) // Mois
                        }
                        if (digitsOnly.length > 4) {
                            append(" / ")
                            append(digitsOnly.substring(4, min(digitsOnly.length, 8))) // Année
                        }
                    }
                    // Autoriser les saisies partielles, mais valider uniquement si complète
                    dateNaissance = TextFieldValue(formatted, TextRange(formatted.length))
                    // Mettre à jour l'erreur en temps réel si la saisie est complète
                    dateErreur = formatted.length == 12 && !isValidDate(formatted)
                },
                label = { Text("Date de naissance") },
                placeholder = { Text("01 / 01 / 2000") },
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_calendar),
                            contentDescription = "Sélecteur de date",
                            tint = Color.Gray
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            if (dateErreur) {
                Text("Date invalide (jour 01-31, mois 01-12, année 1800-$currentYear)", color = Color.Red, fontSize = 12.sp)
            }

            if (showDatePicker) {
                ShowDatePickerDialog(
                    initialDate = dateNaissance.text.ifBlank { "01 / 01 / 2000" },
                    onDateSelected = {
                        dateNaissance = TextFieldValue(it, TextRange(it.length))
                        dateErreur = !isValidDate(it) // Vérifier la validité de la date sélectionnée
                        showDatePicker = false
                    },
                    onDismiss = { showDatePicker = false }
                )
            }

            Spacer(Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = accepterConditions,
                    onCheckedChange = { accepterConditions = it },
                    colors = CheckboxDefaults.colors(checkedColor = OrangePrimary)
                )
                Spacer(Modifier.width(8.dp))
                val annotatedString = buildAnnotatedString {
                    append("En créant un compte, vous acceptez nos ")
                    pushStringAnnotation(tag = "CGU", annotation = "cgu")
                    withStyle(style = SpanStyle(color = OrangePrimary, textDecoration = TextDecoration.Underline)) {
                        append("conditions d'utilisation")
                    }
                    pop()
                    append(" et notre ")
                    pushStringAnnotation(tag = "POLITIQUE", annotation = "politique")
                    withStyle(style = SpanStyle(color = OrangePrimary, textDecoration = TextDecoration.Underline)) {
                        append("politique de confidentialité.")
                    }
                    pop()
                }

                ClickableText(
                    text = annotatedString,
                    onClick = { offset ->
                        annotatedString.getStringAnnotations(tag = "CGU", start = offset, end = offset)
                            .firstOrNull()?.let {
                                // TODO: ouvrir lien CGU
                            }
                        annotatedString.getStringAnnotations(tag = "POLITIQUE", start = offset, end = offset)
                            .firstOrNull()?.let {
                                // TODO: ouvrir lien Politique
                            }
                    },
                    modifier = Modifier.weight(1f),
                    style = LocalTextStyle.current.copy(fontSize = 12.sp)
                )
            }

            if (cguErreur) {
                Text("Vous devez accepter les conditions", color = Color.Red, fontSize = 12.sp)
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    nomErreur = nom.isBlank()
                    prenomErreur = prenom.isBlank()
                    emailErreur = !email.matches(emailRegex)
                    telErreur = !tel.text.matches(phoneRegex)
                    mdpErreur = !mdp.matches(passwordRegex)
                    confirmErreur = mdp != confirmMdp
                    dateErreur = dateNaissance.text.isNotEmpty() && !isValidDate(dateNaissance.text)
                    cguErreur = !accepterConditions

                    val toutEstValide = !nomErreur && !prenomErreur && !emailErreur && !telErreur && !mdpErreur &&
                            !confirmErreur && !dateErreur && !cguErreur

                    if (toutEstValide) {
                        context.startActivity(Intent(context, CreationCompte2Activity::class.java))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary)
            ) {
                Text("Créer mon compte", fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Vous avez déjà un compte ", fontSize = 14.sp)
                Text(
                    "Appla TN ? ",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = OrangePrimary
                )
                Text(
                    "connectez-vous",
                    fontSize = 14.sp,
                    color = OrangePrimary,
                    textDecoration = TextDecoration.Underline
                )
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
fun ShowDatePickerDialog(
    initialDate: String,
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val currentYear = calendar.get(Calendar.YEAR)
    try {
        val parts = initialDate.split("/").map { it.trim() }
        calendar.set(parts[2].toInt(), parts[1].toInt() - 1, parts[0].toInt())
    } catch (_: Exception) {
        calendar.set(2000, 0, 1)
    }

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val jour = dayOfMonth.toString().padStart(2, '0')
            val mois = (month + 1).toString().padStart(2, '0')
            val dateStr = "$jour / $mois / $year"
            onDateSelected(dateStr)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).apply {
        setOnDismissListener { onDismiss() }
        this.datePicker.minDate = Calendar.getInstance().apply { set(1800, 0, 1) }.timeInMillis
        this.datePicker.maxDate = Calendar.getInstance().apply { set(currentYear, 11, 31) }.timeInMillis
        show()
    }
}