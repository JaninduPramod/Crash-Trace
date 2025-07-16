package com.crashtrace.mobile.di

import com.crashtrace.mobile.data.repository.SignUpRepository
import com.crashtrace.mobile.data.repository.LoginRepository
import com.crashtrace.mobile.data.Utils.DataStoreManager
import com.crashtrace.mobile.data.repository.PasswordResetRepository
import com.crashtrace.mobile.data.repository.ProfileRepository
import com.crashtrace.mobile.viewmodel.SignUpViewModel
import com.crashtrace.mobile.viewmodel.LoginViewModel
import com.crashtrace.mobile.viewmodel.PasswordResetViewModel
import com.crashtrace.mobile.viewmodel.ProfileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    // Provide DataStoreManager
    single { DataStoreManager(androidContext()) } // Use androidContext() to inject the application context

    // Provide repositories
    single { SignUpRepository() }
    single { LoginRepository() }
    single { PasswordResetRepository() }
    single { ProfileRepository() }

    // Provide ViewModels
    single { LoginViewModel(get(), get()) } // Inject repository and DataStoreManager
    single { SignUpViewModel(get()) } // Inject repository
    single { PasswordResetViewModel(get()) } // Inject repository
    single { ProfileViewModel(get(), get()) } // Inject repository and DataStoreManager
}