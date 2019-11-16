package com.example.kotlin.koin

import com.example.kotlin.model.provider.FireStoreProvider
import com.example.kotlin.model.provider.RemoteDataProvider
import com.example.kotlin.model.repository.PersonsRepos
import com.example.kotlin.viewmodels.AuthViewModel
import com.example.kotlin.viewmodels.MainViewModel
import com.example.kotlin.viewmodels.PersonViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single<RemoteDataProvider> { FireStoreProvider(get(), get()) }
    single { PersonsRepos(get()) }
}

val authModule = module {
    viewModel { AuthViewModel(get()) }
}

val mainModule = module {
    viewModel { MainViewModel(get()) }
}

val personModule = module {
    viewModel { PersonViewModel(get()) }
}