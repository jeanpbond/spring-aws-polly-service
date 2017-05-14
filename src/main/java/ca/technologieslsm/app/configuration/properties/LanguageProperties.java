package ca.technologieslsm.app.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Properties related to the language.
 */
@Component
@ConfigurationProperties(prefix = "language")
public class LanguageProperties {

    private String gender;
    private String profileDirectory;
    private List<String> voices;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfileDirectory() {
        return profileDirectory;
    }

    public void setProfileDirectory(String profileDirectory) {
        this.profileDirectory = profileDirectory;
    }

    public List<String> getVoices() {
        return voices;
    }

    public void setVoices(List<String> voices) {
        this.voices = voices;
    }
}
