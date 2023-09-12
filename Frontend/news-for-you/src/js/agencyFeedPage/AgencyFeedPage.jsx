import React, { useEffect, useState } from "react";
import AgencyService from "../sevices/agencyService";
import { SmileOutlined } from "@ant-design/icons";
import { useParams } from "react-router-dom";
import { Divider, Result, Skeleton } from "antd";
import NewsService from "../sevices/newsService";
import CategoryService from "../sevices/categoryService";
import util from "../utils/util";

function AgencyFeedPage() {
  const [agency, setAgency] = useState();
  const [category, setCategory] = useState();
  const [loading, setLoading] = useState(true);
  const [allNews, setAllNews] = useState([]);
  let { id } = useParams();

  useEffect(() => {
    AgencyService.getAgencyFeed(id)
      .then((res) => {
        AgencyService.getAgency(res.data.data.agencyId).then((res) => {
          setAgency(res.data.data);
        });
        CategoryService.getCategory(res.data.data.categoryId).then((res) => {
          setCategory(res.data.data);
        });
        let payload = {
          categoryId: res.data.data.categoryId,
          agencyId: res.data.data.agencyId,
        };
        NewsService.getAgencyFeedNews(payload).then((res2) => {
          setAllNews(res2.data.data.newsList);
          setLoading(false);
        });
      })
      .catch((err) => {});
  }, []);

  return (
    <div style={{ minHeight: "82vh", padding: "50px" }}>
      {loading ? (
        <>
          <Skeleton active />
          <Skeleton active />
          <Skeleton active />
        </>
      ) : (
        <>
          <div
            className="admin-right-section-title"
            style={{
              display: "flex",
              alignItems: "center",
              borderBottom: "3px solid #ffffff",
              paddingBottom: "30px",
              margin: "0 50px 40px",
              justifyContent: "center",
            }}
          >
            {agency.agencyLogoPath ? (
              <span>
                <img src={agency.agencyLogoPath} alt={agency.agencyName} />
              </span>
            ) : null}
            {agency.agencyName}
            {" || "}
            {category.categoryTitle}
          </div>
          {allNews.length === 0 ? (
            <Result
              icon={<SmileOutlined />}
              title="No news to display right now !!! Our team is collecting news for you. Stay connected :)"
            />
          ) : (
            <div style={{ margin: "0 75px" }}>
              {allNews.map((news) => {
                return (
                  <>
                    <div
                      className="news-page-title"
                      style={{ maxWidth: "100%" }}
                    >
                      {news.newsTitle}
                    </div>
                    <div
                      className="news-page-description"
                      dangerouslySetInnerHTML={{
                        __html: news.newsDescription,
                      }}
                      style={{ maxWidth: "100%" }}
                    />
                    <div
                      className="read-more"
                      onClick={() => {
                        window.open(news.newsLink, "_blank");
                      }}
                    >
                      read more
                    </div>

                    <div
                      className="posted-on"
                      style={{ margin: "30px 0 50px 20px" }}
                    >
                      Posted On{" "}
                      <span>{util.getTime(news.newsPublishDateTime)}</span>
                    </div>
                    <Divider />
                  </>
                );
              })}
            </div>
          )}
        </>
      )}
    </div>
  );
}

export default AgencyFeedPage;
