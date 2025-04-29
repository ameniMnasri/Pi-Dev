import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './componnents/front_office/template/home/home.component';
import { HeaderBackComponent } from './componnents/back_office/template/header-back/header-back.component';
import { ListTypeActivityComponent } from './componnents/front_office/activities/ListTypeActivity/ListTypeActivity/ListTypeActivity.component';
import { ListActivitiesComponent } from './componnents/front_office/activities/ListActivities/ListActivities.component';
import { AddEditActivitiesComponent } from './componnents/front_office/activities/AddEditActivities/AddEditActivities.component';
import { StatisticsComponent } from './componnents/front_office/activities/Statistics/statistics.component';
import { ExerciseGeneratorComponent } from './componnents/front_office/activities/exercise-generator/exercise-generator.component';
import { AdmincrudComponent } from './componnents/back_office/admincrud/admincrud.component';
import { ForgetpasswordComponent } from './componnents/front_office/user/authComponents/forgetpassword/forgetpassword.component';
import { LoginComponent } from './componnents/front_office/user/authComponents/login/login.component';
import { NotAuthorizedComponent } from './componnents/front_office/user/authComponents/not-authorized/not-authorized.component';
import { RegisterComponent } from './componnents/front_office/user/authComponents/register/register.component';
import { ResetpasswordComponent } from './componnents/front_office/user/authComponents/resetpassword/resetpassword.component';
import { ProfileUpdateComponent } from './componnents/front_office/user/authComponents/user/profile-update/profile-update.component';
import { ProfileComponent } from './componnents/front_office/user/authComponents/user/profile/profile.component';
import { guestGuard } from './guards/guest.guard';

const routes: Routes = [
    {path : 'home', component : HomeComponent},
    {path : 'Back', component : HeaderBackComponent},
    {path : 'ListType', component : ListTypeActivityComponent},
    {path : 'ListActivity', component : ListActivitiesComponent},
    {path : 'statistics', component : StatisticsComponent},
    {path : 'add-edit', component: AddEditActivitiesComponent},
    {path : 'add-edit/:id', component: AddEditActivitiesComponent}, 
    {path : 'activities/details/:id', component: AddEditActivitiesComponent},
    {path : 'exercise-generator', component: ExerciseGeneratorComponent},
    {path : 'crudadmin', component: AdmincrudComponent},
    { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: 'login',
      component: LoginComponent,
      canActivate: [guestGuard]
    },
    { path: 'register',
      component: RegisterComponent,
      canActivate: [guestGuard]
  
    },
    { path: 'forgot-password', component: ForgetpasswordComponent },
    { path: 'reset-password', component: ResetpasswordComponent },
   
    { path: 'not-authorized', component: NotAuthorizedComponent },
    { path: 'profile-update', component: ProfileUpdateComponent },
    { path: 'profile/:username', component: ProfileComponent }
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
