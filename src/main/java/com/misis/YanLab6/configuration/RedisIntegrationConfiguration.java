package com.misis.YanLab6.configuration;

import com.misis.YanLab6.dto.StudentDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.redis.inbound.RedisQueueMessageDrivenEndpoint;
import org.springframework.integration.redis.outbound.RedisQueueOutboundChannelAdapter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;




@Configuration
@EnableIntegration
public class RedisIntegrationConfiguration {


    private static final String QUEUE_STUDENT = "queue:student";  @Bean("studentInboundChannelFlow")
    public IntegrationFlow redisStudentEventInboundChannelFlow(  RedisConnectionFactory redisConnectionFactory,  @Qualifier("studentInboundChannel") MessageChannel channel  ) {
        RedisQueueMessageDrivenEndpoint endpoint =
                new RedisQueueMessageDrivenEndpoint(QUEUE_STUDENT,  redisConnectionFactory);
        Jackson2JsonRedisSerializer<StudentDto> serializer  = new Jackson2JsonRedisSerializer<>(StudentDto.class);  endpoint.setSerializer(serializer);
        endpoint.setBeanName("studentRedisQueueMessageDrivenEndpoint");  return IntegrationFlows
                .from(endpoint)
                .channel(channel)
                .get();
    }
    @Bean("studentOutboundChannelFlow")
    public IntegrationFlow redisStudentEventOutboundChannelFlow(  RedisConnectionFactory redisConnectionFactory,  @Qualifier("studentOutboundChannel") MessageChannel channel  ) {
        Jackson2JsonRedisSerializer<StudentDto> serializer  = new Jackson2JsonRedisSerializer<>(StudentDto.class);  RedisQueueOutboundChannelAdapter channelAdapter =  new RedisQueueOutboundChannelAdapter(QUEUE_STUDENT,  redisConnectionFactory);
        channelAdapter.setSerializer(serializer);
        return IntegrationFlows
                .from(channel)
                .handle(channelAdapter)
                .get();
    }
    @Bean("studentOutboundChannel")
    public SubscribableChannel logEventOutboundChannel() {  PublishSubscribeChannel channel = new PublishSubscribeChannel();  channel.setMaxSubscribers(3);
        channel.setComponentName("studentOutboundChannel");  return channel;
    }
    @Bean("studentInboundChannel")
    public SubscribableChannel logEventInboundChannel() {  PublishSubscribeChannel channel = new PublishSubscribeChannel();  channel.setMaxSubscribers(3);
        channel.setComponentName("studentInboundChannel");  return channel;
    }
}

