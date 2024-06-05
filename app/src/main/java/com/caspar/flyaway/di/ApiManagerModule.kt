import com.caspar.flyaway.model.Currencies
import com.caspar.flyaway.model.CurrencyApiManager
import com.caspar.flyaway.model.CurrencyStatus
import com.caspar.flyaway.model.ExchangeRates
import com.caspar.flyaway.model.Flight
import com.caspar.flyaway.model.FlightApiManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(Singleton::class)
class ApiManagerModule {

    @Singleton
    @Provides
    fun providesFlightService(): Flight {
        return FlightApiManager.retrofit!!.create<Flight>()
    }

    @Singleton
    @Provides
    fun providesStatusService(): CurrencyStatus {
        return CurrencyApiManager.retrofit!!.create<CurrencyStatus>()
    }

    @Singleton
    @Provides
    fun providesCurrenciesService(): Currencies {
        return CurrencyApiManager.retrofit!!.create<Currencies>()
    }

    @Singleton
    @Provides
    fun providesExchangeService(): ExchangeRates {
        return CurrencyApiManager.retrofit!!.create<ExchangeRates>()
    }
}