import { Card } from "antd";
import React from "react";
import util from "../utils/util";
import NewsService from "../sevices/newsService";

function NewsCard({ news }) {
  return (
    <Card
      className="news-card"
      onClick={() => {
        NewsService.increaseClick(news.newsId);
        window.location.href = news.newsLink;
      }}
    >
      <div className="news-title">{news.newsTitle}</div>
      <div
        className="news-page-description"
        style={{ maxWidth: "100%", fontSize: "18px" }}
        dangerouslySetInnerHTML={{
          __html: news.newsDescription.replace("<br/>", ""),
        }}
      />
      <div className="posted-on">
        Posted On <span>{util.getTime(news.newsPublishDateTime)}</span>
      </div>
    </Card>
  );
}

export default NewsCard;
