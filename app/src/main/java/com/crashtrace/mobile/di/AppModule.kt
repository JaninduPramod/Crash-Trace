package com.crashtrace.mobile.di

import com.crashtrace.mobile.data.repository.SignUpRepository
import com.crashtrace.mobile.viewmodel.SignUpViewModel
import org.koin.dsl.module

val appModule = module {
    single { SignUpRepository() }
    single { SignUpViewModel(get()) } // Inject repository into ViewModel
}