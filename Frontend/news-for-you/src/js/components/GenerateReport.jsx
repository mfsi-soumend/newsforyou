import React, { useState } from "react";
import "jspdf-autotable";
import { DownloadOutlined, CloseOutlined } from "@ant-design/icons";
import jsPDF from "jspdf";
import dayjs from "dayjs";
import { Button, DatePicker, Form, Modal, Space, notification } from "antd";
const { RangePicker } = DatePicker;

const rangePresets = [
  {
    label: "Last 7 Days",
    value: [dayjs().add(-7, "d"), dayjs()],
  },
  {
    label: "Last 14 Days",
    value: [dayjs().add(-14, "d"), dayjs()],
  },
  {
    label: "Last 30 Days",
    value: [dayjs().add(-30, "d"), dayjs()],
  },
  {
    label: "Last 90 Days",
    value: [dayjs().add(-90, "d"), dayjs()],
  },
];
const disabledDate = (current) => {
  return current > dayjs().endOf("day");
};
function GenerateReport({ allNews, columns }) {
  const [open, setOpen] = useState(false);
  const [buttonLoading, setButtonLoading] = useState(false);
  const [form] = Form.useForm();
  const downloadPDF = (values) => {
    setButtonLoading(true);
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

    const tableRows = allNews
      .filter(
        (news) =>
          new Date(values.dateRange[0]) <= new Date(news.newsPublishDateTime) &&
          new Date(news.newsPublishDateTime) <= new Date(values.dateRange[1])
      )
      .map((rowData) =>
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

    if (tableRows.length === 0) {
      notification.error({
        message:
          "No news published in the selected range date!! Try again with other range date .",
      });
      setButtonLoading(false);
      return;
    }
    doc.setFontSize(12);
    doc.setFont("helvetica");

    doc.autoTable({
      head: [tableColumns],
      body: tableRows,
      startY: 40,
      columnStyles: {
        0: { columnWidth: 50 },
        1: { columnWidth: 50 },
        2: { columnWidth: 30 },
        3: { columnWidth: 30 },
        4: { columnWidth: 20 },
      },
    });

    doc.save(
      "news_report_" + new Date().toLocaleString().replace(/:/g, "-") + ".pdf"
    );
    setButtonLoading(false);
  };
  return (
    <>
      <Button
        type="secondary"
        onClick={() => {
          setOpen(true);
          form.resetFields();
        }}
        className="download-button"
        icon={<DownloadOutlined />}
      >
        Download as PDF
      </Button>
      <Modal
        title={<div className="modal-title">Download News Report</div>}
        open={open}
        footer={false}
        onCancel={() => setOpen(false)}
        className="add-news-modal"
      >
        <Form
          form={form}
          onFinish={downloadPDF}
          requiredMark={false}
          disabled={buttonLoading}
        >
          <Form.Item
            name="dateRange"
            rules={[
              {
                required: true,
                message: "Select date to download report !!",
              },
            ]}
            style={{ marginTop: "20px" }}
          >
            <RangePicker presets={rangePresets} disabledDate={disabledDate} />
          </Form.Item>
          <Space>
            <Form.Item style={{ margin: "0" }}>
              <Button
                type="secondary"
                htmlType="submit"
                className="user-profile login-button"
                size={30}
                style={{
                  border: "1px solid #8c8c8c",
                  marginTop: "5px",
                }}
                loading={buttonLoading}
              >
                Download
              </Button>
            </Form.Item>
            <Form.Item style={{ margin: "0" }}>
              <Button
                type="secondary"
                className="cancel-button login-button"
                icon={<CloseOutlined />}
                size={30}
                style={{
                  border: "1px solid #8c8c8c",
                  marginTop: "5px",
                }}
                onClick={() => {
                  if (!buttonLoading) {
                    form.resetFields();
                  }
                }}
                disabled={buttonLoading}
              >
                Cancel
              </Button>
            </Form.Item>
          </Space>
        </Form>
      </Modal>
    </>
  );
}

export default GenerateReport;
