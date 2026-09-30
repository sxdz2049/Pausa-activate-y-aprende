package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.PostestEntity
import com.example.data.local.PretestEntity
import com.example.data.model.ActivePause
import com.example.data.model.AssessmentComparison
import com.example.data.model.DigitalArtifact
import com.example.data.model.GroupInfo
import com.example.data.model.PauseCategory
import com.example.data.model.PauseRecord
import com.example.data.model.PedagogicalData
import com.example.data.repository.PausaRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale

enum class AppScreen(val label: String, val icon: String) {
    INICIO("Inicio", "🏠"),
    DIAGNOSTICO("Diagnóstico", "🔎"),
    PAUSAS("Pausas", "🎯"),
    EVALUACION("Evaluación", "📊"),
    RESULTADOS("Resultados", "📈"),
    KIT_DOCENTE("Kit docente", "📚"),
    TRANSFERENCIA("Transferencia", "🌎"),
    PRIVACIDAD("Privacidad", "🔐"),
    CIERRE("Reflexión", "✨")
}

enum class DashboardMode {
    DEMO,
    REAL
}

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PausaRepository

    init {
        val database = AppDatabase.getDatabase(application)
        repository = PausaRepository(database.pausaDao())
        // Pre-populate with realistic demo sample data on first start if needed
        viewModelScope.launch {
            repository.seedDemoClassroomData()
        }
    }

    // Navigation
    private val _currentScreen = MutableStateFlow(AppScreen.INICIO)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
    }

    // Group Information
    private val _groupInfo = MutableStateFlow(GroupInfo())
    val groupInfo: StateFlow<GroupInfo> = _groupInfo.asStateFlow()

    fun updateGroupInfo(
        teacher: String,
        grade: String,
        subject: String,
        studentCount: Int,
        age: String,
        date: String
    ) {
        val updated = GroupInfo(teacher, grade, subject, studentCount, age, date)
        _groupInfo.value = updated
        viewModelScope.launch {
            repository.saveGroup(updated)
            showSnackbar("Datos del grupo guardados exitosamente")
        }
    }

    // Active Pause Selection & Live Player
    private val _selectedPause = MutableStateFlow<ActivePause?>(null)
    val selectedPause: StateFlow<ActivePause?> = _selectedPause.asStateFlow()

    private val _isPlayerOpen = MutableStateFlow(false)
    val isPlayerOpen: StateFlow<Boolean> = _isPlayerOpen.asStateFlow()

    private val _timerSeconds = MutableStateFlow(180) // 3 minutes default
    val timerSeconds: StateFlow<Int> = _timerSeconds.asStateFlow()

    private val _timerTotal = MutableStateFlow(180)
    val timerTotal: StateFlow<Int> = _timerTotal.asStateFlow()

    private val _isTimerRunning = MutableStateFlow(false)
    val isTimerRunning: StateFlow<Boolean> = _isTimerRunning.asStateFlow()

    private val _isTimerCompleted = MutableStateFlow(false)
    val isTimerCompleted: StateFlow<Boolean> = _isTimerCompleted.asStateFlow()

    private var timerJob: Job? = null

    fun selectPause(pause: ActivePause) {
        _selectedPause.value = pause
        val totalSec = pause.durationMinutes * 60
        _timerTotal.value = totalSec
        _timerSeconds.value = totalSec
        _isTimerRunning.value = false
        _isTimerCompleted.value = false
        _isPlayerOpen.value = true
    }

    fun closePlayer() {
        stopTimer()
        _isPlayerOpen.value = false
    }

    fun startTimer() {
        if (_isTimerRunning.value) return
        _isTimerRunning.value = true
        timerJob = viewModelScope.launch {
            while (_isTimerRunning.value && _timerSeconds.value > 0) {
                delay(1000L)
                _timerSeconds.value -= 1
            }
            if (_timerSeconds.value == 0) {
                _isTimerRunning.value = false
                _isTimerCompleted.value = true
            }
        }
    }

    fun pauseTimer() {
        _isTimerRunning.value = false
        timerJob?.cancel()
    }

    fun resetTimer() {
        stopTimer()
        _timerSeconds.value = _timerTotal.value
        _isTimerCompleted.value = false
    }

    fun finishTimer() {
        stopTimer()
        _timerSeconds.value = 0
        _isTimerCompleted.value = true
    }

    private fun stopTimer() {
        _isTimerRunning.value = false
        timerJob?.cancel()
    }

    // Post-Pause Feedback
    val feedbackMood = MutableStateFlow("Muy bien")
    val feedbackAttention = MutableStateFlow("Alta")
    val feedbackMotivation = MutableStateFlow("Alta")
    val feedbackParticipation = MutableStateFlow("Alta")
    val feedbackDisposition = MutableStateFlow("Alta")
    val feedbackNotes = MutableStateFlow("")

    fun savePostPauseRecord() {
        val pause = _selectedPause.value ?: return
        viewModelScope.launch {
            repository.savePauseExecution(
                pauseId = pause.id,
                title = pause.name,
                category = pause.category.displayName,
                mood = feedbackMood.value,
                attention = feedbackAttention.value,
                motivation = feedbackMotivation.value,
                participation = feedbackParticipation.value,
                disposition = feedbackDisposition.value,
                observations = feedbackNotes.value,
                durationSeconds = _timerTotal.value - _timerSeconds.value
            )
            feedbackNotes.value = ""
            closePlayer()
            showSnackbar("¡Registro de pausa activa guardado correctamente!")
        }
    }

    // Pretest State
    private val _pretestStudentId = MutableStateFlow("EST-001")
    val pretestStudentId: StateFlow<String> = _pretestStudentId.asStateFlow()

    private val _pretestAnswers = MutableStateFlow<Map<Int, Int>>(
        mapOf(1 to 3, 2 to 3, 3 to 2, 4 to 3, 5 to 3, 6 to 3, 7 to 4, 8 to 3, 9 to 3, 10 to 3)
    )
    val pretestAnswers: StateFlow<Map<Int, Int>> = _pretestAnswers.asStateFlow()

    fun updatePretestAnswer(questionNumber: Int, score: Int) {
        _pretestAnswers.value = _pretestAnswers.value.toMutableMap().apply {
            put(questionNumber, score)
        }
    }

    fun setPretestStudentId(id: String) {
        _pretestStudentId.value = id
    }

    fun submitPretest() {
        viewModelScope.launch {
            repository.submitPretest(_pretestStudentId.value, _pretestAnswers.value)
            showSnackbar("Diagnóstico inicial de ${_pretestStudentId.value} guardado con éxito")
            // Advance student ID to next anonymous ID
            val currentNum = _pretestStudentId.value.removePrefix("EST-").toIntOrNull() ?: 1
            _pretestStudentId.value = "EST-${String.format(Locale.US, "%03d", currentNum + 1)}"
        }
    }

    // Postest State
    private val _postestStudentId = MutableStateFlow("EST-001")
    val postestStudentId: StateFlow<String> = _postestStudentId.asStateFlow()

    private val _postestAnswers = MutableStateFlow<Map<Int, Int>>(
        mapOf(1 to 4, 2 to 4, 3 to 3, 4 to 4, 5 to 2, 6 to 2, 7 to 4, 8 to 4, 9 to 4, 10 to 4)
    )
    val postestAnswers: StateFlow<Map<Int, Int>> = _postestAnswers.asStateFlow()

    fun updatePostestAnswer(questionNumber: Int, score: Int) {
        _postestAnswers.value = _postestAnswers.value.toMutableMap().apply {
            put(questionNumber, score)
        }
    }

    fun setPostestStudentId(id: String) {
        _postestStudentId.value = id
    }

    fun submitPostest() {
        viewModelScope.launch {
            repository.submitPostest(_postestStudentId.value, _postestAnswers.value)
            showSnackbar("Postest de ${_postestStudentId.value} guardado con éxito")
            val currentNum = _postestStudentId.value.removePrefix("EST-").toIntOrNull() ?: 1
            _postestStudentId.value = "EST-${String.format(Locale.US, "%03d", currentNum + 1)}"
        }
    }

    // Negative polarity recoding toggle (Q5 and Q6)
    private val _recodeNegativePolarity = MutableStateFlow(true)
    val recodeNegativePolarity: StateFlow<Boolean> = _recodeNegativePolarity.asStateFlow()

    fun toggleRecodePolarity() {
        _recodeNegativePolarity.value = !_recodeNegativePolarity.value
    }

    // Repository Flows
    val pauseHistory: StateFlow<List<PauseRecord>> = repository.pauseRecords
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val pretestHistory: StateFlow<List<PretestEntity>> = repository.pretestList
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val postestHistory: StateFlow<List<PostestEntity>> = repository.postestList
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Dashboard Mode & Comparisons
    private val _dashboardMode = MutableStateFlow(DashboardMode.DEMO)
    val dashboardMode: StateFlow<DashboardMode> = _dashboardMode.asStateFlow()

    fun setDashboardMode(mode: DashboardMode) {
        _dashboardMode.value = mode
    }

    val selectedFilterCourse = MutableStateFlow("Todos")

    // Dynamic Real Comparison Flow calculated from Room Pretest and Postest tables
    val realComparisons: StateFlow<List<AssessmentComparison>> = combine(
        repository.pretestList,
        repository.postestList,
        _recodeNegativePolarity
    ) { pretests, postests, recode ->
        if (pretests.isEmpty() || postests.isEmpty()) {
            return@combine emptyList<AssessmentComparison>()
        }

        // Helper to recode score if needed: raw 1..4 -> recoded 4..1 (5 - raw)
        fun score(raw: Int, isNegative: Boolean): Float {
            return if (isNegative && recode) (5 - raw).toFloat() else raw.toFloat()
        }

        // Attention: Q1, Q3, Q6 (neg), Q8
        val preAtn = pretests.map { (score(it.q1, false) + score(it.q3, false) + score(it.q6, true) + score(it.q8, false)) / 4f }.average().toFloat()
        val postAtn = postests.map { (score(it.q1, false) + score(it.q3, false) + score(it.q6, true) + score(it.q8, false)) / 4f }.average().toFloat()

        // Motivation: Q2, Q7, Q9, Q10
        val preMot = pretests.map { (score(it.q2, false) + score(it.q7, false) + score(it.q9, false) + score(it.q10, false)) / 4f }.average().toFloat()
        val postMot = postests.map { (score(it.q2, false) + score(it.q7, false) + score(it.q9, false) + score(it.q10, false)) / 4f }.average().toFloat()

        // Participation: Q4
        val prePart = pretests.map { score(it.q4, false) }.average().toFloat()
        val postPart = postests.map { score(it.q4, false) }.average().toFloat()

        // Disposition/Cansancio: Q5 (neg)
        val preDisp = pretests.map { score(it.q5, true) }.average().toFloat()
        val postDisp = postests.map { score(it.q5, true) }.average().toFloat()

        listOf(
            AssessmentComparison(
                dimension = "ATENCIÓN",
                pretestAvg = preAtn,
                postestAvg = postAtn,
                changeDescription = "Variación de ${String.format(Locale.US, "%+.2f", postAtn - preAtn)} puntos observada en atención.",
                sampleSize = pretests.size
            ),
            AssessmentComparison(
                dimension = "MOTIVACIÓN",
                pretestAvg = preMot,
                postestAvg = postMot,
                changeDescription = "Variación de ${String.format(Locale.US, "%+.2f", postMot - preMot)} puntos en motivación grupal.",
                sampleSize = pretests.size
            ),
            AssessmentComparison(
                dimension = "PARTICIPACIÓN",
                pretestAvg = prePart,
                postestAvg = postPart,
                changeDescription = "Variación de ${String.format(Locale.US, "%+.2f", postPart - prePart)} puntos en participación activa.",
                sampleSize = pretests.size
            ),
            AssessmentComparison(
                dimension = "DISPOSICIÓN",
                pretestAvg = preDisp,
                postestAvg = postDisp,
                changeDescription = "Variación de ${String.format(Locale.US, "%+.2f", postDisp - preDisp)} puntos en disposición y energía.",
                sampleSize = pretests.size
            )
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Active Category Filter for Pause Bank
    private val _selectedCategoryFilter = MutableStateFlow<PauseCategory?>(null)
    val selectedCategoryFilter: StateFlow<PauseCategory?> = _selectedCategoryFilter.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun setCategoryFilter(category: PauseCategory?) {
        _selectedCategoryFilter.value = category
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    // Digital Artifact Viewer
    private val _selectedArtifact = MutableStateFlow<DigitalArtifact?>(null)
    val selectedArtifact: StateFlow<DigitalArtifact?> = _selectedArtifact.asStateFlow()

    fun openArtifact(artifact: DigitalArtifact) {
        _selectedArtifact.value = artifact
    }

    fun closeArtifact() {
        _selectedArtifact.value = null
    }

    fun downloadArtifact(artifact: DigitalArtifact) {
        showSnackbar("Descarga iniciada: ${artifact.title} (${artifact.format})")
    }

    fun downloadTransferKit() {
        showSnackbar("Descargando Paquete Completo de Transferencia Pedagógica (.ZIP)")
    }

    // Snackbar notifications
    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage.asStateFlow()

    fun showSnackbar(message: String) {
        _snackbarMessage.value = message
    }

    fun dismissSnackbar() {
        _snackbarMessage.value = null
    }

    fun resetDemoData() {
        viewModelScope.launch {
            repository.clearDatabase()
            repository.seedDemoClassroomData()
            showSnackbar("Datos demostrativos restablecidos")
        }
    }
}
