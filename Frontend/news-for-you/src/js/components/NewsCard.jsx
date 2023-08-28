import { Card } from "antd";
import React from "react";

function NewsCard({ news }) {
  return (
    <Card className="news-card">
      <div className="news-title">{news.newsTitle}</div>
      <div className="news-description">{news.newsDescription}</div>
    </Card>
  );
}

export default NewsCard;
