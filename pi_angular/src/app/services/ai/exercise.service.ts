import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ExerciseSuggestion } from 'src/app/models/activities/exercise-suggestion.model';

@Injectable({
  providedIn: 'root'
})
export class ExerciseService {

  private apiUrl = 'http://localhost:8085/api/exercises/generate';

  constructor(private http: HttpClient) {}

  generateExercises(goal: string, level: string, timeMinutes: number): Observable<ExerciseSuggestion> {
    const params = new HttpParams()
      .set('goal', goal)
      .set('level', level)
      .set('timeMinutes', timeMinutes.toString());

    return this.http.post<ExerciseSuggestion>(this.apiUrl, null, { params });
  }
}
