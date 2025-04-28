import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ExerciseSuggestion } from 'src/app/models/activities/exercise-suggestion.model';
import { GeminiApiService } from 'src/app/services/ai/gemini-api.service';

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
  goal: string = '';
  level: string = '';
  timeMinutes: number = 15;
  suggestion?: ExerciseSuggestion;
  duration: any;

  constructor(private gem: GeminiApiService) {}

  generate() {
    if (!this.goal || !this.level || this.timeMinutes <= 0) {
      alert('Please fill in all fields correctly.');
      return;
    }

    this.gem.generateExerciseRoutine(this.goal, this.level, this.timeMinutes).subscribe({
      next: (response) => {
        console.log('Generated response:', response); // Déboguer la structure de la réponse
        this.suggestion = response; // Assigner la réponse à l'objet suggestion
      },
      error: (err) => {
        console.error('Error during generation', err);
        this.suggestion = { warmup: [], workout: [], cooldown: [], advice: "Unable to generate routine. Please try again later." };
      }
    });
  }
}