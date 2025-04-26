import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GeminiApiService {
  private apiUrl = 'https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent';
  private apiKey = environment.geminiApiKey;

  constructor(private http: HttpClient) {}

  generateContent(prompt: string): Observable<string> {
    const url = `${this.apiUrl}?key=${this.apiKey}`;
    
    const contextualizedPrompt = `
      [Instructions: You are an assistant who specialises in sports activities, fitness and health.
You should always answer in the context of sports management, wellness, and healthy lifestyle.
Your answers should be informative, encouraging, and suitable for all levels of physical activity.
Keep it concise and to the point in your explanations.
If the issue is not related to sports or health, try to bring it back to that context.]
      
      ${prompt}
    `;
    
    const requestBody = {
      contents: [
        {
          parts: [
            {
              text: contextualizedPrompt
            }
          ]
        }
      ]
    };

    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this.http.post(url, requestBody, { headers }).pipe(
      map((response: any) => {
        if (response && response.candidates && response.candidates.length > 0) {
          return response.candidates[0].content.parts[0].text || "Je n'ai pas de réponse à cette question concernant les activités sportives.";
        }
        return "Sorry, I couldn't generate a response on this sports topic.";
      }),
      catchError(error => {
        console.error('Erreur API Gemini:', error);
        return of(this.getFallbackResponse());
      })
    );
  }

  private getFallbackResponse(): string {
    const responses = ["Regular physical activity is essential to maintain good health and prevent many diseases. I'm here to guide you on your fitness journey.",
"To achieve your fitness goals, it's important to combine a balanced diet with an exercise program that is tailored to your level. How can I help you with your routine?",
"Tracking your sports activities regularly allows you to measure your progress and stay motivated. Our application supports you in this process.",
"A good recovery is as important as the training itself. Don't forget to include rest days in your activity schedule."
];
      
    
    return responses[Math.floor(Math.random() * responses.length)];
  }
} 