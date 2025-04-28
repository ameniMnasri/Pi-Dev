import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { HomeComponent } from './componnents/front_office/template/home/home.component';
import { HeaderComponent } from './componnents/front_office/template/header/header.component';
import { FooterComponent } from './componnents/front_office/template/footer/footer.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


import { HomeBackComponent } from './componnents/back_office/template/home-back/home-back.component';
import { HeaderBackComponent } from './componnents/back_office/template/header-back/header-back.component';
import { AppRoutingModule } from './app-routing.module';
import { ListTypeActivityComponent } from './componnents/front_office/activities/ListTypeActivity/ListTypeActivity/ListTypeActivity.component';
import { HttpClientModule } from '@angular/common/http';
import { ListActivitiesComponent } from './componnents/front_office/activities/ListActivities/ListActivities.component';
import { AddEditActivitiesComponent } from './componnents/front_office/activities/AddEditActivities/AddEditActivities.component';
import { AiChatComponent } from './componnents/front_office/activities/ListActivities/ai-chat/ai-chat.component';
import { CommonModule } from '@angular/common';
import { StatisticsComponent } from './componnents/front_office/activities/Statistics/statistics.component';
import { ExerciseGeneratorComponent } from './componnents/front_office/activities/exercise-generator/exercise-generator.component';

import { LoginComponent } from './componnents/front_office/user/authComponents/login/login.component';
import { RegisterComponent } from './componnents/front_office/user/authComponents/register/register.component';
import { NotAuthorizedComponent } from './componnents/front_office/user/authComponents/not-authorized/not-authorized.component';
import { ForgetpasswordComponent } from './componnents/front_office/user/authComponents/forgetpassword/forgetpassword.component';
import { ResetpasswordComponent } from './componnents/front_office/user/authComponents/resetpassword/resetpassword.component';
import { ProfileUpdateComponent } from './componnents/front_office/user/authComponents/user/profile-update/profile-update.component';
import { ProfileComponent } from './componnents/front_office/user/authComponents/user/profile/profile.component';
import { AdmincrudComponent } from './componnents/back_office/admincrud/admincrud.component';
@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    HomeBackComponent,
    HeaderBackComponent,
    ListTypeActivityComponent,
    ListActivitiesComponent,
    AddEditActivitiesComponent,
    AiChatComponent,
    StatisticsComponent,
    ExerciseGeneratorComponent,
    RegisterComponent,
    ForgetpasswordComponent,
    ResetpasswordComponent,
    
   
   
    NotAuthorizedComponent,
    ForgetpasswordComponent,
    ResetpasswordComponent,
    ProfileUpdateComponent,
    ProfileComponent,
    LoginComponent,
    AdmincrudComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    RouterModule,
   
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
