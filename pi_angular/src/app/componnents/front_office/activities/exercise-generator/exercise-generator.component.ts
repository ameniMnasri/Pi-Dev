import { Component } from '@angular/core';
import { ExerciseService } from 'src/app/services/ai/exercise.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ExerciseSuggestion } from 'src/app/models/activities/exercise-suggestion.model';

@Component({
  selector: 'app-exercise-generator',
  templateUrl: './exercise-generator.component.html',
  styleUrls: ['./exercise-generator.component.css']
})
export class ExerciseGeneratorComponent {
generatedExercises: any;
closePopup() {
throw new Error('Method not implemented.');
}
exercisePlan: any;
generateExercises() {
throw new Error('Method not implemented.');
}

  goal: string = '';
  level: string = '';
  timeMinutes: number = 15;
  suggestion?: ExerciseSuggestion;
duration: any;

  constructor(private exerciseService: ExerciseService) {}

  generate() {
    if (!this.goal || !this.level || this.timeMinutes <= 0) {
      alert('Veuillez remplir tous les champs correctement.');
      return;
    }

    this.exerciseService.generateExercises(this.goal, this.level, this.timeMinutes)
      .subscribe({
        next: (response) => this.suggestion = response,
        error: (err) => console.error('Erreur lors de la génération', err)
      });
  }
}
