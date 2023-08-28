import React, { useState } from "react";
import NewsCard from "../components/NewsCard";

function News() {
  const [allNews, setAllNews] = useState([
    {
      newsId: "64e72af3bf5a352a65a0a599",
      newsTitle: "Introducing our Business News Digest",
      newsDescription:
        "Unveiling the latest in the world of commerce and finance. Stay informed with concise, curated updates. Dive into breaking stories that shape economies, innovation, and markets. Our coverage spans industry trends, mergers, IPOs, and beyond. Delve into exclusive insights, all in one place. Whether you're an entrepreneur, investor, or simply intrigued by the business world, our digest delivers impactful narratives, cutting through the noise to provide you with essential information. Elevate your understanding of global commerce with our succinct, 100-word business news bites. Your go-to source for staying ahead in the corporate landscape.",
      newsPublishDateTime: "2023-08-24T15:33:31.967",
      newsLink: "http://localhost:3000/news/64e72af3bf5a352a65a0a599",
      clickCount: 0,
      categoryId: "64e6f652bfc1637e2a0a4616",
      agencyId: "64e712d01e43747176268199",
    },
    {
      newsId: "64e72af3bf5a352a65a0a599",
      newsTitle: "Introducing our Business News Digest",
      newsDescription:
        "Unveiling the latest in the world of commerce and finance. Stay informed with concise, curated updates. Dive into breaking stories that shape economies, innovation, and markets. Our coverage spans industry trends, mergers, IPOs, and beyond. Delve into exclusive insights, all in one place. Whether you're an entrepreneur, investor, or simply intrigued by the business world, our digest delivers impactful narratives, cutting through the noise to provide you with essential information. Elevate your understanding of global commerce with our succinct, 100-word business news bites. Your go-to source for staying ahead in the corporate landscape.",
      newsPublishDateTime: "2023-08-24T15:33:31.967",
      newsLink: "http://localhost:3000/news/64e72af3bf5a352a65a0a599",
      clickCount: 0,
      categoryId: "64e6f652bfc1637e2a0a4616",
      agencyId: "64e712d01e43747176268199",
    },
    {
      newsId: "64e72af3bf5a352a65a0a599",
      newsTitle: "Introducing our Business News Digest",
      newsDescription:
        "Unveiling the latest in the world of commerce and finance. Stay informed with concise, curated updates. Dive into breaking stories that shape economies, innovation, and markets. Our coverage spans industry trends, mergers, IPOs, and beyond. Delve into exclusive insights, all in one place. Whether you're an entrepreneur, investor, or simply intrigued by the business world, our digest delivers impactful narratives, cutting through the noise to provide you with essential information. Elevate your understanding of global commerce with our succinct, 100-word business news bites. Your go-to source for staying ahead in the corporate landscape.",
      newsPublishDateTime: "2023-08-24T15:33:31.967",
      newsLink: "http://localhost:3000/news/64e72af3bf5a352a65a0a599",
      clickCount: 0,
      categoryId: "64e6f652bfc1637e2a0a4616",
      agencyId: "64e712d01e43747176268199",
    },
    {
      newsId: "64e72af3bf5a352a65a0a599",
      newsTitle: "Introducing our Business News Digest",
      newsDescription:
        "Unveiling the latest in the world of commerce and finance. Stay informed with concise, curated updates. Dive into breaking stories that shape economies, innovation, and markets. Our coverage spans industry trends, mergers, IPOs, and beyond. Delve into exclusive insights, all in one place. Whether you're an entrepreneur, investor, or simply intrigued by the business world, our digest delivers impactful narratives, cutting through the noise to provide you with essential information. Elevate your understanding of global commerce with our succinct, 100-word business news bites. Your go-to source for staying ahead in the corporate landscape.",
      newsPublishDateTime: "2023-08-24T15:33:31.967",
      newsLink: "http://localhost:3000/news/64e72af3bf5a352a65a0a599",
      clickCount: 0,
      categoryId: "64e6f652bfc1637e2a0a4616",
      agencyId: "64e712d01e43747176268199",
    },
    {
      newsId: "64e72af3bf5a352a65a0a599",
      newsTitle: "Introducing our Business News Digest",
      newsDescription:
        "Unveiling the latest in the world of commerce and finance. Stay informed with concise, curated updates. Dive into breaking stories that shape economies, innovation, and markets. Our coverage spans industry trends, mergers, IPOs, and beyond. Delve into exclusive insights, all in one place. Whether you're an entrepreneur, investor, or simply intrigued by the business world, our digest delivers impactful narratives, cutting through the noise to provide you with essential information. Elevate your understanding of global commerce with our succinct, 100-word business news bites. Your go-to source for staying ahead in the corporate landscape.",
      newsPublishDateTime: "2023-08-24T15:33:31.967",
      newsLink: "http://localhost:3000/news/64e72af3bf5a352a65a0a599",
      clickCount: 0,
      categoryId: "64e6f652bfc1637e2a0a4616",
      agencyId: "64e712d01e43747176268199",
    },
    {
      newsId: "64e72af3bf5a352a65a0a599",
      newsTitle: "Introducing our Business News Digest",
      newsDescription:
        "Unveiling the latest in the world of commerce and finance. Stay informed with concise, curated updates. Dive into breaking stories that shape economies, innovation, and markets. Our coverage spans industry trends, mergers, IPOs, and beyond. Delve into exclusive insights, all in one place. Whether you're an entrepreneur, investor, or simply intrigued by the business world, our digest delivers impactful narratives, cutting through the noise to provide you with essential information. Elevate your understanding of global commerce with our succinct, 100-word business news bites. Your go-to source for staying ahead in the corporate landscape.",
      newsPublishDateTime: "2023-08-24T15:33:31.967",
      newsLink: "http://localhost:3000/news/64e72af3bf5a352a65a0a599",
      clickCount: 0,
      categoryId: "64e6f652bfc1637e2a0a4616",
      agencyId: "64e712d01e43747176268199",
    },
    {
      newsId: "64e72af3bf5a352a65a0a599",
      newsTitle: "Introducing our Business News Digest",
      newsDescription:
        "Unveiling the latest in the world of commerce and finance. Stay informed with concise, curated updates. Dive into breaking stories that shape economies, innovation, and markets. Our coverage spans industry trends, mergers, IPOs, and beyond. Delve into exclusive insights, all in one place. Whether you're an entrepreneur, investor, or simply intrigued by the business world, our digest delivers impactful narratives, cutting through the noise to provide you with essential information. Elevate your understanding of global commerce with our succinct, 100-word business news bites. Your go-to source for staying ahead in the corporate landscape.",
      newsPublishDateTime: "2023-08-24T15:33:31.967",
      newsLink: "http://localhost:3000/news/64e72af3bf5a352a65a0a599",
      clickCount: 0,
      categoryId: "64e6f652bfc1637e2a0a4616",
      agencyId: "64e712d01e43747176268199",
    },
    {
      newsId: "64e72af3bf5a352a65a0a599",
      newsTitle: "Introducing our Business News Digest",
      newsDescription:
        "Unveiling the latest in the world of commerce and finance. Stay informed with concise, curated updates. Dive into breaking stories that shape economies, innovation, and markets. Our coverage spans industry trends, mergers, IPOs, and beyond. Delve into exclusive insights, all in one place. Whether you're an entrepreneur, investor, or simply intrigued by the business world, our digest delivers impactful narratives, cutting through the noise to provide you with essential information. Elevate your understanding of global commerce with our succinct, 100-word business news bites. Your go-to source for staying ahead in the corporate landscape.",
      newsPublishDateTime: "2023-08-24T15:33:31.967",
      newsLink: "http://localhost:3000/news/64e72af3bf5a352a65a0a599",
      clickCount: 0,
      categoryId: "64e6f652bfc1637e2a0a4616",
      agencyId: "64e712d01e43747176268199",
    },
    {
      newsId: "64e72af3bf5a352a65a0a599",
      newsTitle: "Introducing our Business News Digest",
      newsDescription:
        "Unveiling the latest in the world of commerce and finance. Stay informed with concise, curated updates. Dive into breaking stories that shape economies, innovation, and markets. Our coverage spans industry trends, mergers, IPOs, and beyond. Delve into exclusive insights, all in one place. Whether you're an entrepreneur, investor, or simply intrigued by the business world, our digest delivers impactful narratives, cutting through the noise to provide you with essential information. Elevate your understanding of global commerce with our succinct, 100-word business news bites. Your go-to source for staying ahead in the corporate landscape.",
      newsPublishDateTime: "2023-08-24T15:33:31.967",
      newsLink: "http://localhost:3000/news/64e72af3bf5a352a65a0a599",
      clickCount: 0,
      categoryId: "64e6f652bfc1637e2a0a4616",
      agencyId: "64e712d01e43747176268199",
    },
  ]);
  return (
    <div className="news-card-wrapper">
      {allNews.map((news) => {
        return <NewsCard news={news} id={news.newsId} />;
      })}
    </div>
  );
}

export default News;
