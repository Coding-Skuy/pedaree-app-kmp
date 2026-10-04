import com.pedaree.pantry.statusKedaluwarsa
import com.pedaree.pantry.statusStok
import kotlin.test.Test
import kotlin.test.assertEquals

class PantryTest {
    @Test
    fun kedaluwarsaLewat() {
        assertEquals("lewat", statusKedaluwarsa(-1))
    }

    @Test
    fun stokMenipis() {
        assertEquals("menipis", statusStok(100.0, 250.0))
    }
}
