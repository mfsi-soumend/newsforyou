package com.newsforyou.newsservice.service.impl;

import java.io.StringReader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.newsforyou.newsservice.exception.InvalidRequestException;
import com.newsforyou.newsservice.model.News;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RssFeedService {

	private final WebClient webClient;


	public Mono<List<News>> fetchAndParseRssFeed(String feedUrl) {
	    return webClient.get()
	            .uri(feedUrl)
	            .retrieve()
	            .bodyToMono(String.class)
	            .flatMap(this::parseRssFeed);
	}

    private Mono<List<News>> parseRssFeed(String rssContent) {
        try {
        	SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new StringReader(rssContent));

            List<News> rssItems = new ArrayList<>();
            for (SyndEntry entry : feed.getEntries()) {
                News item = new News();
                item.setNewsTitle(entry.getTitle());
                item.setNewsDescription(entry.getDescription().getValue());
                item.setNewsLink(entry.getLink());
                // Convert the Date to LocalDateTime
                Date publishedDate = entry.getPublishedDate();
                if (publishedDate != null) {
                    Instant instant = publishedDate.toInstant();
                    LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
                    item.setNewsPublishDateTime(localDateTime);
                }
                rssItems.add(item);
            }

            return Mono.just(rssItems);
        } catch (Exception e) {
        	throw new InvalidRequestException(e.getMessage());
        }
    }
}

