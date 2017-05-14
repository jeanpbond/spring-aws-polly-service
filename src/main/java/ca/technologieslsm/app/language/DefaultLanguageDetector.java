package ca.technologieslsm.app.language;

import ca.technologieslsm.app.configuration.properties.LanguageProperties;
import ca.technologieslsm.app.exception.LanguageProfileNotFoundException;
import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;
import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URISyntaxException;
import java.util.Optional;

@Component
public class DefaultLanguageDetector implements  LanguageDetector {

    private final static String DEFAULT_LANGUAGE = "en";

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LanguageProperties languageProperties;

    @PostConstruct
    public void init() {
        try {
            DetectorFactory.loadProfile(new File(Detector.class.getResource(languageProperties.getProfileDirectory())
                    .toURI()));
        } catch (LangDetectException | URISyntaxException e) {
            LOG.error("Error while loading language profiles", e);
            Throwables.propagateIfPossible(new LanguageProfileNotFoundException(e),
                    LanguageProfileNotFoundException.class);
        }
    }

    @Override
    public String detectLanguage(String text) {
        String result = null;
        try {
            Detector detector = DetectorFactory.create();
            detector.append(text);
            result = detector.detect();
        } catch (LangDetectException e) {
            LOG.warn("Error while trying to detect the language", e);
        }
        return Optional.ofNullable(result).orElse(DEFAULT_LANGUAGE);
    }
}
