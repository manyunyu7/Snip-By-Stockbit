import com.feylabs.core.helper.wrapper.ResponseState
import com.feylabs.qris_bni.FakeTransactionData
import com.feylabs.qris_bni.domain.repository.QrisRepository
import com.feylabs.qris_bni.domain.uimodel.BalanceUiModel
import com.feylabs.qris_bni.domain.uimodel.TransactionUiModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class QrisRepositoryTest {

    private val qrisRepository: QrisRepository = mockk()

    @Test
    fun `test addTransaction`() = runBlocking {
        // Arrange
        coEvery {
            qrisRepository.addTransaction(any(), any(), any(), any())
        } returns flowOf(ResponseState.Success("Transaction added successfully"))

        // Act
        val result = qrisRepository.addTransaction("Merchant", 100.0, "Purchase", System.currentTimeMillis()).toList()

        // Assert
        assertEquals(ResponseState.Success("Transaction added successfully"), result.first())
    }

    @Test
    fun `test getTransaction`() = runBlocking {
        // Arrange
        val fakeTransactionList = FakeTransactionData.generateFakeTransactionList(100)
        coEvery { qrisRepository.getTransaction() } returns flowOf(ResponseState.Success(fakeTransactionList))

        // Act
        val result = qrisRepository.getTransaction().toList()

        // Assert
        assertEquals(ResponseState.Success(fakeTransactionList), result.first())
    }

    @Test
    fun `test getBalance`() = runBlocking {
        // Arrange
        val fakeBalance = BalanceUiModel(
            20000000.0,System.currentTimeMillis()
        )
        coEvery { qrisRepository.getBalance() } returns flowOf(ResponseState.Success(fakeBalance))

        // Act
        val result = qrisRepository.getBalance().toList()

        // Assert
        assertEquals(ResponseState.Success(fakeBalance), result.first())
    }
}
