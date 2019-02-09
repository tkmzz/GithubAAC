package br.com.luiz.githubaac.di.modules

import android.app.Application
import android.arch.persistence.room.Room
import br.com.luiz.githubaac.data.local.MyDatabase
import br.com.luiz.githubaac.data.local.dao.UserDao
import br.com.luiz.githubaac.data.remote.UserWebService
import br.com.luiz.githubaac.data.repositories.UserRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideDatabase(application:Application): MyDatabase {
        return Room.databaseBuilder(
                application,
                MyDatabase::class.java,
                "github.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: MyDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit{
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http://api.github.com")
                .build()
    }

    @Provides
    @Singleton
    fun provideUserWebService(retrofit: Retrofit): UserWebService {
        return retrofit.create(UserWebService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
            userWebService: UserWebService,
            userDao: UserDao,
            executor: Executor
    ): UserRepository{
        return UserRepository(
                userWebService,
                userDao,
                executor
        )
    }
}