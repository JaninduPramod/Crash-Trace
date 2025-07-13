package com.crashtrace.mobile.di

import com.crashtrace.mobile.data.repository.SignUpRepository
import com.crashtrace.mobile.data.repository.LoginRepository
import com.crashtrace.mobile.viewmodel.SignUpViewModel
import com.crashtrace.mobile.viewmodel.LoginViewModel
import org.koin.dsl.module

val appModule = module {
    single { SignUpRepository() }
    single { SignUpViewModel(get()) } // Inject repository into ViewModel

    single { LoginRepository() }
    single { LoginViewModel(get()) } // Inject repository into ViewModel
}

