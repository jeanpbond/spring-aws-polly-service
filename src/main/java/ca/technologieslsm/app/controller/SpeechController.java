package ca.technologieslsm.app.controller;

import ca.technologieslsm.app.service.PollyService;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.util.IOUtils;
import javaslang.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/speech")
public class SpeechController {

    @Autowired
    private PollyService pollyService;

    @GetMapping(produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<byte[]> getAudio(@RequestParam("text") String text, @RequestParam("name") String fileName) {
        return getAudioResponse(text, fileName);
    }

    @PostMapping(produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<byte[]> postAudio(@RequestParam("text") String text, @RequestParam("name") String fileName) {
        return getAudioResponse(text, fileName);
    }

    private ResponseEntity<byte[]> getAudioResponse(String text, String fileName) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=" + fileName + ".mp3");
        responseHeaders.add("Content-Type", "audio/mpeg");

        return Try.of(() -> pollyService.synthesizeSpeech(text, OutputFormat.Mp3))
                .mapTry(IOUtils::toByteArray)
                .map(bytes -> new ResponseEntity<>(bytes, responseHeaders, HttpStatus.OK))
                .getOrElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
