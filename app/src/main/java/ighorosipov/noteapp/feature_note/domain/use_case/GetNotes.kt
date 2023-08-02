package ighorosipov.noteapp.feature_note.domain.use_case

import ighorosipov.noteapp.feature_note.domain.model.Note
import ighorosipov.noteapp.feature_note.domain.repository.NoteRepository
import ighorosipov.noteapp.feature_note.domain.util.NoteOrder
import ighorosipov.noteapp.feature_note.domain.util.NoteOrder.*
import ighorosipov.noteapp.feature_note.domain.util.OrderType.Ascending
import ighorosipov.noteapp.feature_note.domain.util.OrderType.Descending
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(
    private val repository: NoteRepository
) {

    operator fun invoke(
        noteOrder: NoteOrder = Date(Descending)
    ): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when(noteOrder.orderType) {
                is Ascending -> {
                    when(noteOrder) {
                        is Title -> notes.sortedBy { it.title.lowercase() }
                        is Date -> notes.sortedBy { it.timestamp }
                        is Color -> notes.sortedBy { it.color }
                    }
                }
                is Descending -> {
                    when(noteOrder) {
                        is Title -> notes.sortedByDescending { it.title.lowercase() }
                        is Date -> notes.sortedByDescending { it.timestamp }
                        is Color -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }

}