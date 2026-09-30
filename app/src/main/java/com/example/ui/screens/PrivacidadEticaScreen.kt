package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.SectionHeader
import com.example.ui.theme.DeepNavy
import com.example.ui.theme.EnergyYellow
import com.example.ui.theme.MintGreen
import com.example.ui.theme.OceanTeal
import com.example.ui.viewmodel.AppViewModel

@Composable
fun PrivacidadEticaScreen(
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
            SectionHeader(
                title = "PRIVACIDAD Y ÉTICA",
                subtitle = "Eje 5: Consideraciones éticas, legales y de propiedad intelectual"
            )
        }

        // Ficha Técnica Académica: ACERCA DEL PROYECTO
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DeepNavy),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("acerca_del_proyecto_card")
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.School, contentDescription = null, tint = EnergyYellow)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "ACERCA DEL PROYECTO",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(14.dp))

                    AcademicSpecItem("Nombre de la experiencia:", "Pausa, Actívate y Aprende")
                    AcademicSpecItem("Tipo de propuesta:", "Experiencia Educativa Exitosa – Opción A")
                    AcademicSpecItem("Propósito:", "Transferencia y replicabilidad pedagógica en instituciones educativas")
                    AcademicSpecItem("Nivel de madurez:", "SRL 5 – Validación en contexto educativo relevante")
                    AcademicSpecItem("Meta del ecosistema:", "Facilitar que otros docentes puedan implementar, evaluar y adaptar la estrategia con datos empíricos observables.")
                }
            }
        }

        // Principios de Privacidad y Uso Responsable
        item {
            SectionHeader(
                title = "PRIVACIDAD Y USO RESPONSABLE",
                subtitle = "Pautas obligatorias para salvaguardar la intimidad de los estudiantes"
            )
        }

        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    val ethicalRules = listOf(
                        "No publicar nombres completos de estudiantes" to "Toda la información se procesa mediante códigos anónimos únicos (ej. EST-001, EST-002).",
                        "No recopilar datos personales innecesarios" to "Solo se solicitan percepciones académicas y caracterización grupal indispensable.",
                        "Presentar resultados de forma agregada" to "Las estadísticas se difunden como promedios y distribuciones grupales, nunca individuales.",
                        "Proteger la información recopilada" to "Almacenamiento local seguro en el dispositivo sin envío a servidores no autorizados.",
                        "Solicitar autorizaciones para fotos y videos" to "Consentimiento informado firmado por padres o tutores antes de cualquier registro audiovisual.",
                        "Respetar derechos de autor" to "Todo el material musical, lúdico o metodológico cita las fuentes correspondientes.",
                        "Dar crédito a recursos externos" to "Atribución explícita a la comunidad pedagógica de referencia."
                    )

                    ethicalRules.forEach { (title, desc) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(OceanTeal.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("✓", color = OceanTeal, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepNavy
                                )
                                Text(
                                    text = desc,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }

        // Propiedad Intelectual & Licencia
        item {
            SectionHeader(
                title = "PROPIEDAD INTELECTUAL",
                subtitle = "Marco legal de licenciamiento y uso libre no comercial"
            )
        }

        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = OceanTeal
                    ) {
                        Text(
                            text = "LICENCIA: Creative Commons CC BY-NC-SA 4.0",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Atribución – NoComercial – CompartirIgual",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = DeepNavy
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "“Los materiales pueden compartirse y adaptarse respetando la atribución, el uso no comercial y las condiciones de la licencia.”",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Esta licencia garantiza que cualquier docente o directivo escolar pueda replicar, adaptar y contextualizar los 8 artefactos a sus propias asignaturas y niveles de escolaridad, siempre que preserve los créditos y mantenga el carácter gratuito y educativo.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun AcademicSpecItem(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = EnergyYellow
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White
        )
    }
}
