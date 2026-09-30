package com.example.data.model

import androidx.compose.ui.graphics.Color

enum class PauseCategory(val displayName: String, val icon: String, val description: String) {
    ATENCION("Atención", "🧠", "Ejercicios para enfocar la concentración y memoria de trabajo"),
    MOVIMIENTO("Movimiento", "🏃", "Activación muscular y oxigenación para romper la fatiga postural"),
    RITMO("Ritmo", "🎵", "Secuencias coordinadas y percusión corporal para despertar los sentidos"),
    COOPERACION("Cooperación", "🤝", "Dinámicas grupales para reforzar empatía y trabajo en equipo"),
    REGULACION("Regulación", "🌬️", "Respiración consciente y serenidad para calmar la sobreexcitación")
}

data class ActivePause(
    val id: Int,
    val name: String,
    val category: PauseCategory,
    val durationMinutes: Int,
    val purpose: String,
    val difficulty: String, // Fácil, Intermedio, Dinámico
    val materials: String,
    val instructions: List<String>,
    val adaptation: String
)

data class LikertQuestion(
    val id: Int,
    val number: Int,
    val text: String,
    val category: String, // ATENCIÓN, MOTIVACIÓN, PARTICIPACIÓN, CANSANCIO/DISPOSICIÓN
    val isNegativePolarity: Boolean = false // Q5 and Q6 are negative polarity
)

data class GroupInfo(
    val teacherName: String = "Docente María Gómez",
    val grade: String = "8° Grado A",
    val subject: String = "Ciencias Naturales",
    val studentCount: Int = 28,
    val approxAge: String = "13 - 15 años",
    val date: String = "2026-09-30"
)

data class PauseRecord(
    val id: Long = 0,
    val pauseId: Int,
    val pauseName: String,
    val categoryName: String,
    val timestamp: Long = System.currentTimeMillis(),
    val formattedDate: String,
    val groupReaction: String, // "Muy bien", "Bien", "Regular", "Poco"
    val attentionLevel: String, // "Baja", "Media", "Alta"
    val motivationLevel: String,
    val participationLevel: String,
    val dispositionLevel: String,
    val observations: String
)

data class DigitalArtifact(
    val id: String,
    val title: String,
    val code: String,
    val category: String,
    val description: String,
    val format: String,
    val pagesOrDuration: String,
    val icon: String,
    val keyPoints: List<String>
)

data class ImplementationStep(
    val number: Int,
    val title: String,
    val subtitle: String,
    val actions: List<String>,
    val teacherTip: String
)

data class TransductionConcept(
    val academicTerm: String,
    val simplifiedMeaning: String,
    val classroomApplication: String
)

data class AssessmentComparison(
    val dimension: String,
    val pretestAvg: Float,
    val postestAvg: Float,
    val changeDescription: String,
    val sampleSize: Int
)

object PedagogicalData {
    val QUESTIONS = listOf(
        LikertQuestion(1, 1, "Logro concentrarme durante las actividades de clase.", "ATENCIÓN", false),
        LikertQuestion(2, 2, "Me siento motivado(a) para participar en clase.", "MOTIVACIÓN", false),
        LikertQuestion(3, 3, "Mantengo mi atención durante las explicaciones.", "ATENCIÓN", false),
        LikertQuestion(4, 4, "Participo activamente en las actividades.", "PARTICIPACIÓN", false),
        LikertQuestion(5, 5, "Me siento cansado(a) durante las clases.", "CANSANCIO/DISPOSICIÓN", true),
        LikertQuestion(6, 6, "Me distraigo fácilmente durante las actividades.", "ATENCIÓN", true),
        LikertQuestion(7, 7, "Me gusta participar en actividades que incluyen movimiento.", "MOTIVACIÓN", false),
        LikertQuestion(8, 8, "Después de moverme un poco puedo concentrarme nuevamente.", "ATENCIÓN", false),
        LikertQuestion(9, 9, "Las actividades dinámicas hacen que quiera participar.", "MOTIVACIÓN", false),
        LikertQuestion(10, 10, "Considero que puedo aprender mejor cuando estoy activo(a).", "MOTIVACIÓN", false)
    )

    val ACTIVE_PAUSES: List<ActivePause> = listOf(
        ActivePause(
            id = 1,
            name = "Semáforo corporal",
            category = PauseCategory.ATENCION,
            durationMinutes = 3,
            purpose = "Fortalecer atención, reacción y coordinación motora mediante códigos cromáticos.",
            difficulty = "Fácil",
            materials = "Tarjetas de colores o señas con manos (opcional)",
            instructions = listOf(
                "🟢 VERDE: Caminar o mover brazos en el lugar activamente.",
                "🟡 AMARILLO: Moverse en cámara lenta con control muscular.",
                "🔴 ROJO: Quedarse congelado como estatua sin parpadear.",
                "🔵 AZUL: Realizar un movimiento libre y creativo de estiramiento."
            ),
            adaptation = "Se puede realizar sentado moviendo solo brazos y torso si el aula tiene pasillos estrechos."
        ),
        ActivePause(
            id = 2,
            name = "Espejo",
            category = PauseCategory.COOPERACION,
            durationMinutes = 3,
            purpose = "Desarrollar empatía kinestésica, conexión visual y sincronización en parejas.",
            difficulty = "Fácil",
            materials = "Ninguno",
            instructions = listOf(
                "Organizarse en parejas frente a frente a un metro de distancia.",
                "El estudiante A realiza movimientos lentos (gestos, estiramientos de cuello, brazos).",
                "El estudiante B imita simultáneamente en tiempo real como si fuera su reflejo.",
                "Al escuchar la palmada del docente a los 90 segundos, se invierten los roles."
            ),
            adaptation = "Pueden realizarlo también a distancia a través de los pasillos sin moverse de su pupitre."
        ),
        ActivePause(
            id = 3,
            name = "Sigue el ritmo",
            category = PauseCategory.RITMO,
            durationMinutes = 3,
            purpose = "Despertar la atención auditiva y la precisión motriz con patrones de percusión corporal.",
            difficulty = "Intermedio",
            materials = "Ninguno (palmas, muslos y chasquidos)",
            instructions = listOf(
                "El docente o líder establece un compás de 4 tiempos: muslo, muslo, palma, chasquido.",
                "Todo el grupo replica el patrón al unísono manteniendo la velocidad constante.",
                "Se introduce una variación gradual: agregar un doble aplauso o un toque de hombro.",
                "Se finaliza con una respiración profunda sincronizada en el último tiempo."
            ),
            adaptation = "Adaptar el ritmo más lento para estudiantes con desafíos de coordinación."
        ),
        ActivePause(
            id = 4,
            name = "Derecha–izquierda",
            category = PauseCategory.ATENCION,
            durationMinutes = 3,
            purpose = "Estimular la lateralidad y la disociación hemisférica cerebral.",
            difficulty = "Intermedio",
            materials = "Ninguno",
            instructions = listOf(
                "Mano derecha toca la oreja izquierda; simultáneamente mano izquierda toca la nariz.",
                "A la señal del docente ('¡Cambio!'), alternar rápidamente la posición de las manos.",
                "Intentar realizar 10 alternancias fluidas sin perder la referencia cruzada.",
                "Aumentar progresivamente la velocidad con una sonrisa grupal."
            ),
            adaptation = "Realizar primero movimientos individuales de brazos antes del cruce simultáneo."
        ),
        ActivePause(
            id = 5,
            name = "Estatuas musicales",
            category = PauseCategory.MOVIMIENTO,
            durationMinutes = 3,
            purpose = "Liberar tensión acumulada y activar la respuesta inhibitoria motora.",
            difficulty = "Fácil",
            materials = "Música rítmica o silbato/palmada",
            instructions = listOf(
                "Al sonar la música, todos se mueven bailando suavemente en su puesto.",
                "Cuando la música se detiene repentinamente, todos congelan su postura.",
                "Sostener la postura de equilibrio durante 5 segundos sin reír ni tambalearse.",
                "Repetir tres ciclos de 40 segundos con posturas cada vez más expresivas."
            ),
            adaptation = "En aulas con ruido restringido, usar gestos visuales del docente en lugar de audio."
        ),
        ActivePause(
            id = 6,
            name = "Respira y activa",
            category = PauseCategory.REGULACION,
            durationMinutes = 4,
            purpose = "Regular el sistema nervioso y oxigenar el cerebro para predisponer al estudio.",
            difficulty = "Fácil",
            materials = "Ninguno",
            instructions = listOf(
                "Postura erguida con hombros relajados y pies apoyados firmes en el suelo.",
                "Inhalar por la nariz en 4 tiempos elevando lentamente los brazos al cielo.",
                "Retener el aire 2 tiempos sintiendo la expansión de la caja torácica.",
                "Exhalar por la boca en 6 tiempos bajando los brazos y soltando la tensión.",
                "Repetir 5 ciclos conscientes con ojos semicerrados."
            ),
            adaptation = "Ideal para momentos posteriores a exámenes o antes de explicaciones complejas."
        ),
        ActivePause(
            id = 7,
            name = "Líder del movimiento",
            category = PauseCategory.COOPERACION,
            durationMinutes = 4,
            purpose = "Fomentar la cohesión del aula y la agudeza perceptiva visual.",
            difficulty = "Intermedio",
            materials = "Ninguno",
            instructions = listOf(
                "Un estudiante sale 15 segundos al frente dando la espalda al salón.",
                "El grupo elige silenciosamente a un 'líder secreto' que iniciará movimientos.",
                "El estudiante regresa al centro e intenta descubrir quién es el líder.",
                "El grupo debe cambiar de movimiento imitando al líder con sutileza y complicidad."
            ),
            adaptation = "Jugar en semicírculo para máxima visibilidad de todos los participantes."
        ),
        ActivePause(
            id = 8,
            name = "Palmas encadenadas",
            category = PauseCategory.RITMO,
            durationMinutes = 3,
            purpose = "Desarrollar la concentración secuencial y el sentido de continuidad comunitaria.",
            difficulty = "Fácil",
            materials = "Ninguno",
            instructions = listOf(
                "El docente inicia un aplauso simple mirando fijamente hacia la primera fila.",
                "Cada estudiante debe dar un aplauso tan pronto como su compañero de al lado aplauda.",
                "La 'ola de aplausos' debe viajar por todo el salón a modo de circuito eléctrico veloz.",
                "Cronometrar el tiempo total de la vuelta y desafiar al grupo a mejorar el récord."
            ),
            adaptation = "Hacer la cadena en sentido horario y luego antihorario para variar la atención."
        ),
        ActivePause(
            id = 9,
            name = "Número y movimiento",
            category = PauseCategory.ATENCION,
            durationMinutes = 3,
            purpose = "Vincular consignas numéricas con respuestas motrices rápidas.",
            difficulty = "Intermedio",
            materials = "Ninguno",
            instructions = listOf(
                "El docente asigna códigos: 1 = Levantar brazos, 2 = Sentarse/levantarse, 3 = Giro de 180°.",
                "El docente dice operaciones matemáticas sencillas: '¡Dos más uno!' -> Grupo ejecuta 3.",
                "'¡Diez dividido en diez!' -> Grupo ejecuta 1 levantando los brazos.",
                "Acelerar las operaciones durante 2 minutos continuos de alta agilidad mental."
            ),
            adaptation = "Alinear las operaciones con el tema que se esté tratando en clase."
        ),
        ActivePause(
            id = 10,
            name = "Animal en movimiento",
            category = PauseCategory.MOVIMIENTO,
            durationMinutes = 3,
            purpose = "Estiramiento lúdico de la columna y articulaciones mediante imaginería natural.",
            difficulty = "Fácil",
            materials = "Ninguno",
            instructions = listOf(
                "🦒 JIRAFA: Estirar cuello y brazos lo más alto posible en puntas de pies.",
                "🐢 TORTUGA: Encoger hombros hacia las orejas y rotar suavemente la cabeza.",
                "🦅 ÁGUILA: Extender los brazos en cruz y simular un planeo amplio y controlado.",
                "🐸 RANA: Flexión suave de rodillas en cuclillas y retorno con impulso suave."
            ),
            adaptation = "Permite a los estudiantes explorar movilidad corporal sin inhibiciones."
        ),
        ActivePause(
            id = 11,
            name = "La ola",
            category = PauseCategory.COOPERACION,
            durationMinutes = 2,
            purpose = "Generar entusiasmo colectivo instantáneo y sincronía kinésica.",
            difficulty = "Fácil",
            materials = "Ninguno",
            instructions = listOf(
                "Desde el primer estudiante de la izquierda, se levanta de su asiento con brazos arriba.",
                "Inmediatamente el siguiente lo secunda creando una onda visible continua.",
                "Al llegar al extremo derecho, la ola regresa con mayor velocidad y sonido vocal ('¡Wooo!').",
                "Completar tres recorridos de ida y vuelta para reactivar la energía del aula."
            ),
            adaptation = "Si están sentados en mesas hexagonales, la ola circula en espiral."
        ),
        ActivePause(
            id = 12,
            name = "Cambio de lugar",
            category = PauseCategory.MOVIMIENTO,
            durationMinutes = 3,
            purpose = "Romper la rigidez del asiento y promover la interacción entre pares.",
            difficulty = "Intermedio",
            materials = "Ninguno",
            instructions = listOf(
                "El docente da una consigna: 'Cambian de lugar quienes tengan zapatos negros'.",
                "Los estudiantes aludidos se levantan con cuidado y buscan otro asiento libre.",
                "Siguientes rondas: 'Quienes hayan desayunado fruta', 'Quienes cumplan años en número par'.",
                "Finalizar pidiendo que saluden con el puño a su nuevo compañero de fila."
            ),
            adaptation = "En aulas con puestos fijos, cambiar de lugar de pie en el mismo pasillo."
        ),
        ActivePause(
            id = 13,
            name = "Reto de equilibrio",
            category = PauseCategory.ATENCION,
            durationMinutes = 3,
            purpose = "Fortalecer la propiocepción, el tono muscular postural y el autocontrol.",
            difficulty = "Avanzado",
            materials = "Ninguno",
            instructions = listOf(
                "Pararse derecho en un solo pie (apoyo monopodal) elevando la otra rodilla a 90 grados.",
                "Mantener la mirada fija en un punto inmóvil del aula durante 20 segundos.",
                "Subir la dificultad: cerrar el ojo izquierdo manteniendo la postura sin caer.",
                "Cambiar de pierna y repetir con respiraciones lentas y profundas."
            ),
            adaptation = "Estudiantes con dificultades pueden apoyar una yema de dedo sobre su mesa."
        ),
        ActivePause(
            id = 14,
            name = "Cuenta y muévete",
            category = PauseCategory.RITMO,
            durationMinutes = 3,
            purpose = "Coordinar cálculo mental progresivo con patrones de movimiento alternado.",
            difficulty = "Intermedio",
            materials = "Ninguno",
            instructions = listOf(
                "Contar en voz alta del 1 al 20 en grupo.",
                "Cada vez que aparezca un múltiplo de 3 o contenga el número 3, se aplaude en vez de hablar.",
                "Cada vez que aparezca un múltiplo de 5, se da un salto suave o toque de hombro.",
                "Si alguien se equivoca, todo el salón exhala profundamente y retoma desde el 1."
            ),
            adaptation = "Permite dinamizar conceptos matemáticos durante la pausa."
        ),
        ActivePause(
            id = 15,
            name = "Eco corporal",
            category = PauseCategory.RITMO,
            durationMinutes = 3,
            purpose = "Estimular la escucha activa y la memoria procedimental de secuencias cortas.",
            difficulty = "Fácil",
            materials = "Ninguno",
            instructions = listOf(
                "El docente realiza una secuencia rítmica de 3 pasos: golpe de pecho + 2 palmas.",
                "El grupo responde exactamente igual como un eco acústico.",
                "Aumentar a 4 pasos: chasquido + golpe de muslo + palma + pisotón sutil.",
                "Un estudiante voluntario toma el rol de creador del eco para desafiar a sus compañeros."
            ),
            adaptation = "Excelente para transiciones entre módulos temáticos pesados."
        ),
        ActivePause(
            id = 16,
            name = "Semáforo de emociones",
            category = PauseCategory.REGULACION,
            durationMinutes = 4,
            purpose = "Facilitar la autoconciencia socioemocional y la expresión corporal autorregulada.",
            difficulty = "Fácil",
            materials = "Ninguno",
            instructions = listOf(
                "El docente nombra un estado: 'Sobrecargado', 'Inquieto', 'Desmotivado', 'Dispuesto'.",
                "Los estudiantes adoptan una postura corporal que represente cómo se sienten hoy.",
                "A la voz de '¡Transformación!', cambian su postura hacia una actitud de apertura y calma.",
                "Cerrar con 3 inhalaciones llevando las manos al pecho para conectar con el bienestar."
            ),
            adaptation = "Fomenta la confianza mutua y la empatía antes de continuar las clases."
        ),
        ActivePause(
            id = 17,
            name = "Cruce coordinado",
            category = PauseCategory.ATENCION,
            durationMinutes = 3,
            purpose = "Estimular la neuroplasticidad mediante cruce de línea media corporal.",
            difficulty = "Avanzado",
            materials = "Ninguno",
            instructions = listOf(
                "De pie con pies separados al ancho de hombros.",
                "Codo derecho toca rodilla izquierda cruzando la línea media del cuerpo.",
                "Luego codo izquierdo toca rodilla derecha en un movimiento continuo y rítmico.",
                "Variación: tocar el talón contrario por detrás con la mano opuesta.",
                "Realizar 15 repeticiones fluidas asegurando una respiración regular."
            ),
            adaptation = "Se puede hacer sentado elevando las rodillas hacia el codo opuesto."
        ),
        ActivePause(
            id = 18,
            name = "Palabra y gesto",
            category = PauseCategory.ATENCION,
            durationMinutes = 3,
            purpose = "Ejercitar la flexibilidad cognitiva y la disociación semántica-motora.",
            difficulty = "Intermedio",
            materials = "Ninguno",
            instructions = listOf(
                "Cuando el docente diga 'Cielo', los estudiantes apuntan hacia el piso.",
                "Cuando diga 'Tierra', los estudiantes apuntan hacia el techo.",
                "Cuando diga 'Silencio', todos dan un aplauso enérgico.",
                "Cuando diga 'Ruido', todos ponen dedo en labios en silencio absoluto.",
                "Divertidísimo para resetear la fatiga atencional a través de la incongruencia lúdica."
            ),
            adaptation = "Genera risas constructivas y restablece la dopamina cerebral."
        ),
        ActivePause(
            id = 19,
            name = "Reto cooperativo",
            category = PauseCategory.COOPERACION,
            durationMinutes = 4,
            purpose = "Desarrollar confianza, soporte mutuo y resolución kinésica en equipos.",
            difficulty = "Intermedio",
            materials = "Ninguno",
            instructions = listOf(
                "En parejas, apoyarse espalda con espalda con brazos entrelazados firmemente.",
                "Flexionar lentamente las rodillas bajando a media sentadilla coordinada.",
                "Mantener el apoyo mutuo durante 10 segundos sintiendo el equilibrio compartido.",
                "Subir juntos con la fuerza de piernas sin soltar los brazos.",
                "Finalizar chocando las manos y felicitando al compañero de equipo."
            ),
            adaptation = "En caso de diferencias de altura, realizar empuje palmar frontal suave."
        ),
        ActivePause(
            id = 20,
            name = "Vuelve a aprender",
            category = PauseCategory.REGULACION,
            durationMinutes = 3,
            purpose = "Cerrar la activación con integración somática y disposición atencional plena.",
            difficulty = "Fácil",
            materials = "Ninguno",
            instructions = listOf(
                "Cerrar los ojos, frotar las palmas de las manos hasta generar calor agradable.",
                "Colocar las palmas tibias sobre los ojos en forma de cuenco sin presionar.",
                "Sentir el calor relajante en los músculos oculares durante 30 segundos.",
                "Bajar las manos despacio, abrir los ojos con una sonrisa y sentarse con columna erguida.",
                "El docente concluye: 'Cuerpo activado, mente despierta, listos para aprender'."
            ),
            adaptation = "Perfecta para cerrar cualquier bloque de pausas y reanudar la clase."
        )
    )

    val DIGITAL_ARTIFACTS: List<DigitalArtifact> = listOf(
        DigitalArtifact(
            id = "ART-01",
            title = "Guía Metodológica",
            code = "GM-2026",
            category = "Pedagogía y Fundamentación",
            description = "Manual integral para la planificación, contextualización e implementación estructurada de pausas activas en el aula escolar.",
            format = "PDF Interactivo",
            pagesOrDuration = "36 páginas",
            icon = "📘",
            keyPoints = listOf(
                "Marco pedagógico y neurociencia aplicada a la atención",
                "Estrategias de inserción curricular en bloques de 45 a 90 minutos",
                "Gestión del clima de aula y transición sin perder el control de grupo",
                "Adaptaciones para estudiantes con necesidades educativas diversas"
            )
        ),
        DigitalArtifact(
            id = "ART-02",
            title = "Banco de Pausas Activas",
            code = "BPA-20",
            category = "Fichas Didácticas",
            description = "Fichero completo con las 20 pausas clasificadas por categoría, duración, propósito y adaptaciones para espacios reducidos.",
            format = "Fichero Digital Imprimible",
            pagesOrDuration = "24 fichas",
            icon = "🎯",
            keyPoints = listOf(
                "5 dimensiones: Atención, Movimiento, Ritmo, Cooperación y Regulación",
                "Instrucciones paso a paso con códigos visuales",
                "Glosario de adaptaciones según la edad de los estudiantes",
                "Matriz de selección rápida según el nivel de cansancio del grupo"
            )
        ),
        DigitalArtifact(
            id = "ART-03",
            title = "Cuestionario Pretest",
            code = "PRE-10",
            category = "Instrumento Diagnóstico",
            description = "Escala Likert de 10 afirmaciones para caracterizar la percepción inicial de atención, motivación y cansancio.",
            format = "Formulario Digital / Imprimible",
            pagesOrDuration = "4 páginas",
            icon = "📊",
            keyPoints = listOf(
                "10 ítems estructurados con escala 1 a 4 (Nunca a Siempre)",
                "Claves de recodificación para ítems de polaridad negativa (ítems 5 y 6)",
                "Pautas de anonimización ética con identificadores EST-001...EST-999",
                "Guía de aplicación de 10 minutos para el docente"
            )
        ),
        DigitalArtifact(
            id = "ART-04",
            title = "Cuestionario Postest",
            code = "POST-10",
            category = "Instrumento de Evaluación",
            description = "Instrumento homólogo al pretest para contrastar la percepción de los estudiantes tras el ciclo de intervención.",
            format = "Formulario Digital / Imprimible",
            pagesOrDuration = "4 páginas",
            icon = "📊",
            keyPoints = listOf(
                "Mismas 10 dimensiones para asegurar comparabilidad estadística",
                "Cálculo automático de deltas de variación porcentual",
                "Sección de valoración cualitativa de agrado hacia las pausas",
                "Matriz de triangulación de datos"
            )
        ),
        DigitalArtifact(
            id = "ART-05",
            title = "Rúbrica de Observación",
            code = "RUB-OB",
            category = "Evaluación Cualitativa",
            description = "Matriz de descriptores conductuales para evaluar los cambios en atención focalizada, colaboración y disposición corporal.",
            format = "Matriz PDF / Excel",
            pagesOrDuration = "6 niveles",
            icon = "📋",
            keyPoints = listOf(
                "Criterios de observación directa durante las clases",
                "Indicadores conductuales observables y no especulativos",
                "Escala de desempeño: Inicial, En desarrollo, Consolidado, Destacado",
                "Pauta de registro de incidencias del clima de aula"
            )
        ),
        DigitalArtifact(
            id = "ART-06",
            title = "Registro de Seguimiento",
            code = "BIT-REG",
            category = "Seguimiento Diario",
            description = "Bitácora pedagógica para documentar la frecuencia, hora de aplicación, respuesta del grupo y observaciones inmediatas.",
            format = "Bitácora Digital",
            pagesOrDuration = "Hojas de registro",
            icon = "👀",
            keyPoints = listOf(
                "Campos rápidos para registrar en menos de 60 segundos",
                "Monitoreo de reacciones emocionales y niveles de atención",
                "Espacio para notas contextuales (ej. clima, viernes por la tarde)",
                "Conexión con el repositorio local de datos"
            )
        ),
        DigitalArtifact(
            id = "ART-07",
            title = "Plantilla de Resultados",
            code = "PL-RES",
            category = "Analítica y Transferencia",
            description = "Plantilla automatizada con fórmulas para condensar pretest/postest, calcular promedios y graficar variaciones.",
            format = "Hoja de Cálculo / Dashboard",
            pagesOrDuration = "Hojas automatizadas",
            icon = "📈",
            keyPoints = listOf(
                "Fórmulas precargadas para promedio sobre escala de 4 puntos",
                "Generación automática de gráficos de barras comparativos",
                "Cálculo de Tasa de Adopción y Frecuencia de uso",
                "Sección de conclusiones preliminares con lenguaje riguroso"
            )
        ),
        DigitalArtifact(
            id = "ART-08",
            title = "Videos Demostrativos",
            code = "VID-DEM",
            category = "Recurso Audiovisual",
            description = "Cápsulas formativas audiovisuales que muestran a docentes reales facilitando las pausas activas en aulas reales.",
            format = "Cápsulas Multimedia HD",
            pagesOrDuration = "8 videos (3-5 min)",
            icon = "🎥",
            keyPoints = listOf(
                "Modelado de instrucciones claras y modulación de voz",
                "Manejo de tiempos y transiciones con aplausos y señales",
                "Testimonios docentes sobre retos y adaptaciones",
                "Demostración de pausas en espacios reducidos con pupitres"
            )
        )
    )

    val TRANSDUCTION_TABLE: List<TransductionConcept> = listOf(
        TransductionConcept(
            academicTerm = "ATENCIÓN SOSTENIDA Y SELECTIVA",
            simplifiedMeaning = "¿Qué tan concentrado está el grupo?",
            classroomApplication = "Capacidad del estudiante para mantenerse enfocado en la explicación sin distraerse con estímulos irrelevantes."
        ),
        TransductionConcept(
            academicTerm = "MOTIVACIÓN INTRÍNSECA Y ENGAGEMENT",
            simplifiedMeaning = "¿Qué tanta disposición tiene para participar?",
            classroomApplication = "Ganas y curiosidad genuina de involucrarse en las tareas escolares y responder a las preguntas."
        ),
        TransductionConcept(
            academicTerm = "PAUSA ACTIVA PEDAGÓGICA",
            simplifiedMeaning = "Actividad breve de movimiento durante la clase.",
            classroomApplication = "Paréntesis planificado de 3 a 5 minutos que reactiva la circulación y el enfoque cognitivo."
        ),
        TransductionConcept(
            academicTerm = "INTERVENCIÓN CUASI-EXPERIMENTAL",
            simplifiedMeaning = "Aplicación planificada de las pausas.",
            classroomApplication = "Implementar la estrategia sistemáticamente durante 4 a 6 semanas registrando cada sesión."
        ),
        TransductionConcept(
            academicTerm = "EVALUACIÓN DE IMPACTO",
            simplifiedMeaning = "Comparación de información antes y después.",
            classroomApplication = "Analizar los cambios perceptuales entre el diagnóstico inicial y la consulta final."
        ),
        TransductionConcept(
            academicTerm = "TRANSFERENCIA PEDAGÓGICA",
            simplifiedMeaning = "Aplicación de la estrategia por otros docentes.",
            classroomApplication = "Empoderar a colegas de otras áreas para que adopten y adapten la metodología en sus propias aulas."
        )
    )

    val IMPLEMENTATION_STEPS: List<ImplementationStep> = listOf(
        ImplementationStep(
            number = 1,
            title = "Diagnosticar",
            subtitle = "Conoce el estado inicial del aula",
            actions = listOf(
                "Diligenciar los datos de caracterización del grupo (área, curso, número de estudiantes).",
                "Aplicar el cuestionario Pretest de 10 afirmaciones con identificadores anónimos EST-001.",
                "Identificar si predomina fatiga física, dispersión atencional o apatía participativa."
            ),
            teacherTip = "Asegurar a los estudiantes que no es una prueba con calificación, sino una consulta de opinión."
        ),
        ImplementationStep(
            number = 2,
            title = "Planificar",
            subtitle = "Establece los momentos oportunos",
            actions = listOf(
                "Revisar el cronograma semanal e identificar bloques de clase superiores a 60 minutos.",
                "Definir el momento pedagógico ideal: a la mitad del bloque o antes de la actividad práctica.",
                "Establecer una meta inicial de 2 a 3 pausas activas por semana."
            ),
            teacherTip = "Evitar pausar en medio de una explicación crítica; buscar transiciones naturales."
        ),
        ImplementationStep(
            number = 3,
            title = "Preparar",
            subtitle = "Acondiciona el espacio y las normas",
            actions = listOf(
                "Acordar con el grupo la señal de detención (ejemplo: tres palmadas o campana).",
                "Asegurar que los pasillos estén despejados de mochilas para evitar tropiezos.",
                "Seleccionar previamente la pausa del banco según la energía observada en el salón."
            ),
            teacherTip = "La anticipación reduce la ansiedad y previene el desorden."
        ),
        ImplementationStep(
            number = 4,
            title = "Implementar",
            subtitle = "Ejecuta con entusiasmo y claridad",
            actions = listOf(
                "Activar el temporizador visual interactivo de 3 minutos de la aplicación.",
                "Explicar la consigna en menos de 30 segundos con modelado corporal directo.",
                "Participar activamente junto con los estudiantes para generar confianza y contagio positivo."
            ),
            teacherTip = "Si el docente se mueve con entusiasmo, el grupo responde con alegría inmediata."
        ),
        ImplementationStep(
            number = 5,
            title = "Observar",
            subtitle = "Monitorea la respuesta grupal",
            actions = listOf(
                "Registrar visualmente si hay estudiantes tímidos, hiperactivos o desconectados.",
                "Evaluar la sincronía, el respeto y la receptividad hacia las consignas.",
                "Identificar si la actividad generó relajación, energía o sobreexcitación."
            ),
            teacherTip = "El lenguaje corporal del estudiante es el mejor termómetro pedagógico."
        ),
        ImplementationStep(
            number = 6,
            title = "Evaluar",
            subtitle = "Aplica el Postest al concluir el ciclo",
            actions = listOf(
                "Completar la bitácora breve de seguimiento inmediatamente después de la pausa.",
                "Tras 4 semanas de implementación continua, aplicar el cuestionario Postest.",
                "Contrastar las valoraciones individuales y grupales."
            ),
            teacherTip = "Dedica 60 segundos a guardar la bitácora mientras los estudiantes retoman el cuaderno."
        ),
        ImplementationStep(
            number = 7,
            title = "Analizar",
            subtitle = "Examina tendencias y deltas con rigor",
            actions = listOf(
                "Revisar los gráficos de barras comparativos en el Dashboard de Resultados.",
                "Evaluar si se observaron cambios favorables en atención y motivación.",
                "Contrastar con las calificaciones y entregas de tareas del periodo."
            ),
            teacherTip = "Recordar el principio ético: no asegurar causalidad automática, sino incidencia favorable."
        ),
        ImplementationStep(
            number = 8,
            title = "Transferir",
            subtitle = "Comparte el conocimiento con la comunidad",
            actions = listOf(
                "Compartir el Kit Docente con colegas de área y directivos institucionales.",
                "Presentar los indicadores de adopción y testimonios en comités curriculares.",
                "Acompañar a un docente par en su primera implementación guiada."
            ),
            teacherTip = "El conocimiento educativo crece cuando se convierte en práctica colectiva compartida."
        )
    )

    val USER_JOURNEY_STEPS = listOf(
        "👩‍🏫 DOCENTE" to "Punto de partida profesional",
        "🔎 DESCUBRE" to "Identifica la necesidad en el aula",
        "📊 DIAGNOSTICA" to "Aplica el Pretest anónimo",
        "🎯 SELECCIONA" to "Elige la pausa según la fatiga",
        "🏃 IMPLEMENTA" to "Activa el temporizador de 3 min",
        "👀 OBSERVA" to "Monitorea el clima y disposición",
        "📋 REGISTRA" to "Guarda la bitácora inmediata",
        "📈 EVALÚA" to "Compara Pretest vs Postest",
        "🔄 ADAPTA" to "Ajusta duración y dificultad",
        "🌎 TRANSFIERE" to "Empodera a su comunidad escolar"
    )

    // Baseline Demo Statistics (clearly identified as DEMO in the UI)
    val DEMO_COMPARISONS = listOf(
        AssessmentComparison(
            dimension = "ATENCIÓN",
            pretestAvg = 2.45f,
            postestAvg = 3.35f,
            changeDescription = "Cambio favorable de +0.90 puntos en capacidad de foco y concentración sostenida.",
            sampleSize = 28
        ),
        AssessmentComparison(
            dimension = "MOTIVACIÓN",
            pretestAvg = 2.60f,
            postestAvg = 3.52f,
            changeDescription = "Incremento de +0.92 puntos en disposición hacia las tareas escolares.",
            sampleSize = 28
        ),
        AssessmentComparison(
            dimension = "PARTICIPACIÓN",
            pretestAvg = 2.30f,
            postestAvg = 3.28f,
            changeDescription = "Aumento de +0.98 puntos en respuestas activas y preguntas en clase.",
            sampleSize = 28
        ),
        AssessmentComparison(
            dimension = "DISPOSICIÓN",
            pretestAvg = 2.10f, // Recoded negative tiredness into positive readiness
            postestAvg = 3.15f,
            changeDescription = "Mejora de +1.05 puntos en energía corporal y reducción de fatiga perceptible.",
            sampleSize = 28
        )
    )
}
