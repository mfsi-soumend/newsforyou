import React, { useEffect, useState } from "react";
import util from "../utils/util";
import { Button, Space, Table, Tag, notification } from "antd";
import jsPDF from "jspdf";
import "jspdf-autotable";
import NewsService from "../sevices/newsService";
import { DownloadOutlined } from "@ant-design/icons";
import AddNews from "./AddNews";

function ManageNews() {
  const [allNews, setAllNews] = useState([]);
  const [newsCount, setNewsCount] = useState(0);
  const [pageSize, setPageSize] = useState(5);
  const [loadingTable, setLoadingTable] = useState(true);

  const downloadPDF = () => {
    const doc = new jsPDF();
    const title = "News Report";
    doc.setFontSize(16);
    doc.setFont("helvetica", "bold");
    const titleWidth =
      (doc.getStringUnitWidth(title) * doc.internal.getFontSize()) /
      doc.internal.scaleFactor;
    const pageWidth = doc.internal.pageSize.getWidth();
    const centerX = (pageWidth - titleWidth) / 2;
    doc.text(title, centerX, 20);
    const tableColumns = columns
      .filter((column) => column.key !== "action")
      .map((column) => column.title);

    const tableRows = allNews.map((rowData) =>
      columns
        .filter((column) => column.key !== "action")
        .map((column) => {
          if (column.key !== "tags") {
            const cellData = rowData[column.dataIndex];
            const truncatedCellData =
              cellData.length > 100
                ? cellData.substring(0, 100) + "..."
                : cellData;

            return truncatedCellData;
          } else {
            return rowData.agencyId + ", " + rowData.categoryId;
          }
        })
    );

    doc.setFontSize(12);
    doc.setFont("helvetica");

    doc.autoTable({
      head: [tableColumns],
      body: tableRows,
      startY: 40,
    });

    doc.save(
      "news_report_" + new Date().toLocaleString().replace(/:/g, "-") + ".pdf"
    );
  };
  const deleteNews = (newsId) => {
    setLoadingTable(true);
    NewsService.deleteNews(newsId)
      .then((res) => {
        notification.success({ message: res.data.data });
        setLoadingTable(false);
        const filteredAllNews = allNews.filter(
          (news) => news.newsId !== newsId
        );
        setAllNews(filteredAllNews);
        setNewsCount(newsCount - 1);
      })
      .catch((err) => {
        notification.error({ message: err.response.data.error.message });
        setLoadingTable(false);
      });
  };

  const columns = [
    {
      title: "Title",
      dataIndex: "newsTitle",
      key: "newsTitle",
      width: 250,
      render: (__, { newsTitle, newsLink }) => (
        <a href={newsLink} target="_blank" className="table-news-tiitle">
          {newsTitle}
        </a>
      ),
    },
    {
      title: "Description",
      dataIndex: "newsDescription",
      key: "newsDescription",
      render: (description) => (
        <div
          className="news-description"
          dangerouslySetInnerHTML={{
            __html: description.replace("<br/>", ""),
          }}
        />
      ),
    },
    {
      title: "Publish On",
      dataIndex: "newsPublishDateTime",
      key: "newsPublishDateTime",
      width: 200,
      sorter: (a, b) =>
        new Date(a.newsPublishDateTime) - new Date(b.newsPublishDateTime),
      sortDirections: ["ascend", "descend"],
      render: (newsPublishDateTime) => util.getTime(newsPublishDateTime),
    },
    {
      title: "Tags",
      key: "tags",
      dataIndex: "tags",
      render: (_, { agencyId, categoryId }) => (
        <>
          {[agencyId, categoryId].map((tag, index) => {
            let color = tag.length > 5 ? "geekblue" : "green";
            if (index === 1) {
              color = "volcano";
            }
            return (
              <Tag color={color} key={tag} className="table-tags">
                {tag}
              </Tag>
            );
          })}
        </>
      ),
    },
    {
      title: "Clicks",
      dataIndex: "clickCount",
      key: "clickCount",
      sorter: (a, b) => a.clickCount - b.clickCount,
    },
    {
      title: "Action",
      key: "action",
      render: (_, record) => (
        <a
          onClick={() => {
            deleteNews(record.newsId);
          }}
        >
          Delete
        </a>
      ),
    },
  ];
  useEffect(() => {
    NewsService.getAllNewsForTable()
      .then((res) => {
        setAllNews(res.data.data.newsList);
        setNewsCount(res.data.data.totalNewsCount);
        setLoadingTable(false);
      })
      .catch((err) => {
        notification.error({ message: err.response.data.error.message });
        setLoadingTable(false);
      });
  }, []);
  return (
    <div style={{ minHeight: "78vh" }}>
      <div className="manage-news-header">
        <div
          className="admin-right-section-title"
          style={{
            display: "flex",
            alignItems: "center",
            justifyContent: "flex-start",
            marginBottom: 0,
          }}
        >
          Manage News<span className="news-count">{newsCount}</span>
        </div>
        <Space className="manage-news-buttons" size={12}>
          <AddNews setAllNews={setAllNews} setNewsCount={setNewsCount} />
          <Button
            type="secondary"
            onClick={downloadPDF}
            className="download-button"
            icon={<DownloadOutlined />}
          >
            Download as PDF
          </Button>
        </Space>
      </div>

      <Table
        columns={columns}
        dataSource={allNews}
        loading={loadingTable}
        className="news-table"
        pagination={{
          pageSize: pageSize, // Number of items per page
          total: newsCount, // Total number of items
          position: ["bottomCenter"],
          showSizeChanger: true, // Show "Show [X] items per page" dropdown
          showQuickJumper: true, // Show quick jump input
          pageSizeOptions: [5, 10, 20, 50],
          onShowSizeChange: (current, size) => {
            setPageSize(size);
          },
        }}
      />
    </div>
  );
}

export default ManageNews;
