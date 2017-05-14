package ca.technologieslsm.app.configuration;

import ca.technologieslsm.app.configuration.properties.AWSProperties;
import ca.technologieslsm.app.configuration.properties.LanguageProperties;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPollyClient;
import com.amazonaws.services.polly.model.DescribeVoicesRequest;
import com.amazonaws.services.polly.model.DescribeVoicesResult;
import com.amazonaws.services.polly.model.Voice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

@Configuration
public class PollyConfiguration {

    @Autowired
    private AWSProperties awsProperties;

    @Autowired
    private LanguageProperties languageProperties;

    @Bean
    public Region region() {
        return Region.getRegion(Regions.US_EAST_1);
    }

    @Bean
    public AmazonPollyClient amazonPollyClient() {
        AmazonPollyClient client = new AmazonPollyClient(new BasicAWSCredentials(awsProperties.getAccessKeyId()
                , awsProperties.getSecretAccessKey()), new ClientConfiguration());
        client.setRegion(region());
        return client;
    }

    @Bean
    public Map<String, Voice> voices(AmazonPollyClient client) {
        DescribeVoicesRequest describeVoicesRequest = new DescribeVoicesRequest();
        DescribeVoicesResult describeVoicesResult = client.describeVoices(describeVoicesRequest);

        /* Filter by gender and voices selected + remove duplicate by language code */
        return describeVoicesResult.getVoices().stream()
                .filter(v -> v.getGender().equals(languageProperties.getGender()))
                .filter(v -> languageProperties.getVoices().contains(v.getLanguageCode()))
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(
                        comparingInt(v -> v.getLanguageCode().hashCode()))), ArrayList::new)).stream()
                .collect(Collectors.toMap(v -> v.getLanguageCode().split("-")[0], Function.identity()));
    }
}
