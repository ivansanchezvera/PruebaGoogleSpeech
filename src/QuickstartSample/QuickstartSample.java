/*
  Copyright 2017, Google Inc.
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
      http://www.apache.org/licenses/LICENSE-2.0
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/

package QuickstartSample;

// [START speech_quickstart]
// Imports the Google Cloud client library
import com.google.cloud.speech.spi.v1beta1.SpeechClient;
import com.google.cloud.speech.v1beta1.RecognitionAudio;
import com.google.cloud.speech.v1beta1.RecognitionConfig;
import com.google.cloud.speech.v1beta1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1beta1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1beta1.SpeechRecognitionResult;
import com.google.cloud.speech.v1beta1.SyncRecognizeResponse;
import com.google.protobuf.ByteString;
import com.google.protobuf.Descriptors.FieldDescriptor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class QuickstartSample {
  public static void main(String... args) throws Exception {
    // Instantiates a client
    
	  
	  SpeechClient speech = SpeechClient.create();
    

    // The path to the audio file to transcribe
    String fileName = "./resources/DiaLluvioso.flac";

    // Reads the audio file into memory
    Path path = Paths.get(fileName);
    byte[] data = Files.readAllBytes(path);
    ByteString audioBytes = ByteString.copyFrom(data);

    // Builds the sync recognize request
    RecognitionConfig config = RecognitionConfig.newBuilder()
    	.setLanguageCode("es-EC")
        .setEncoding(AudioEncoding.FLAC)
        .setSampleRate(44100)
        .build();
    RecognitionAudio audio = RecognitionAudio.newBuilder()
        .setContent(audioBytes)
        .build();

    // Performs speech recognition on the audio file
    SyncRecognizeResponse response = speech.syncRecognize(config, audio);
    List<SpeechRecognitionResult> results = response.getResultsList();

    for (SpeechRecognitionResult result: results) {
      List<SpeechRecognitionAlternative> alternatives = result.getAlternativesList();
      for (SpeechRecognitionAlternative alternative: alternatives) {
        System.out.printf("Transcription: %s%n", alternative.getTranscript());
      }
    }
    System.out.println("Fin!!!");
  }
}
// [END speech_quickstart]