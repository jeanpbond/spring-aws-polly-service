package ca.technologieslsm.app.service;

import ca.technologieslsm.app.language.LanguageDetector;
import com.amazonaws.services.polly.AmazonPollyClient;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;
import com.amazonaws.services.polly.model.Voice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Service
public class PollyServiceImpl implements PollyService {

    @Autowired
    private AmazonPollyClient client;

    @Autowired
    private LanguageDetector languageDetector;

    @Autowired
    private Map<String, Voice> voices;

    @Override
    public InputStream synthesizeSpeech(String text, OutputFormat format) throws IOException {
        Voice voice = getVoiceBasedOnText(text);

        SynthesizeSpeechRequest synthReq =
                new SynthesizeSpeechRequest().withText(text).withVoiceId(voice.getId())
                        .withOutputFormat(format);
        SynthesizeSpeechResult synthRes = client.synthesizeSpeech(synthReq);

        return synthRes.getAudioStream();
    }

    @Override
    public Voice getVoiceBasedOnText(String text) {
        String language = languageDetector.detectLanguage(text);
        return voices.get(language);
    }
}
