package ca.technologieslsm.app.service;

import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.Voice;

import java.io.IOException;
import java.io.InputStream;

public interface PollyService {

    InputStream synthesizeSpeech(String text, OutputFormat format) throws IOException;

    Voice getVoiceBasedOnText(String text);
}
