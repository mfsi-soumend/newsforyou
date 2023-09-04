import React, { useEffect, useState } from "react";
import AgencyService from "../sevices/agencyService";
import {
  Button,
  Card,
  Form,
  Input,
  Result,
  Skeleton,
  notification,
} from "antd";
import {
  SmileOutlined,
  SaveOutlined,
  EditTwoTone,
  CloseOutlined,
} from "@ant-design/icons";

function Agency() {
  const [allAgency, setAllAgency] = useState([]);
  const [loading, setLoading] = useState(true);
  const [buttonLoading, setButtonLoading] = useState(false);
  const [updateButtonLoading, setUpdateButtonLoading] = useState(false);
  const [selected, setSelected] = useState(null);
  const [form] = Form.useForm();
  const [updateForm] = Form.useForm();
  const updateAgency = (values) => {
    setUpdateButtonLoading(true);
    let payload = values;
    payload["agencyId"] = selected;
    AgencyService.updateAgency(payload)
      .then((res) => {
        notification.success({
          message: values.agencyName + " successfully updated !!",
        });
        updateForm.resetFields();
        setSelected(null);
        setUpdateButtonLoading(false);
        form.resetFields();
        setLoading(true);
        AgencyService.getAllAgency().then((res) => {
          setAllAgency(res.data.data.agencyResponseList);
          setLoading(false);
        });
      })
      .catch((err) => {
        notification.error({ message: err.response.data.error.message });
        setUpdateButtonLoading(false);
      });
  };
  const addAgency = (values) => {
    setButtonLoading(true);
    AgencyService.createAgency(values)
      .then((res) => {
        notification.success({
          message: values.agencyName + " successfully created !!",
        });
        setButtonLoading(false);
        form.resetFields();
        setLoading(true);
        AgencyService.getAllAgency().then((res) => {
          setAllAgency(res.data.data.agencyResponseList);
          setLoading(false);
        });
      })
      .catch((err) => {
        notification.error({ message: err.response.data.error.message });
        setButtonLoading(false);
      });
  };
  useEffect(() => {
    AgencyService.getAllAgency().then((res) => {
      setAllAgency(res.data.data.agencyResponseList);
      setLoading(false);
    });
  }, []);
  return loading ? (
    <Skeleton active />
  ) : (
    <div style={{ minHeight: "78vh" }}>
      <div className="admin-right-section-title">Categories</div>
      {allAgency.length === 0 ? (
        <Result
          icon={<SmileOutlined />}
          title="No news to display right now !!! Our team is collecting news for you. Stay connected :)"
        />
      ) : (
        <div className="agency-card-wrapper">
          <Card className="agency-card" key="add-agency">
            <div className="add-agency-title">Add New Agency </div>
            <Form
              form={form}
              onFinish={addAgency}
              layout="vertical"
              requiredMark={false}
              disabled={buttonLoading}
            >
              <Form.Item
                name="agencyName"
                rules={[
                  {
                    required: true,
                    message: "Add Agency Name !!",
                  },
                ]}
              >
                <Input
                  className="login-input"
                  id="agencyName"
                  placeholder="*Enter Agency Name"
                />
              </Form.Item>
              <Form.Item name="agencyLogoPath">
                <Input
                  className="login-input"
                  id="agencyLogoPath"
                  placeholder="Enter Agency Logo url"
                />
              </Form.Item>
              <Form.Item style={{ marginBottom: 0 }}>
                <Button
                  type="secondary"
                  htmlType="submit"
                  className="user-profile login-button"
                  icon={<SaveOutlined />}
                  size={30}
                  style={{ border: "1px solid #8c8c8c", marginTop: "5px" }}
                  loading={buttonLoading}
                >
                  Save{" "}
                </Button>
              </Form.Item>
            </Form>
          </Card>
          {allAgency.map((agency) => {
            return (
              <Card className="agency-card" key={agency.agencyId}>
                {selected && selected === agency.agencyId ? (
                  <>
                    <div className="agency-update-logo">
                      <Button
                        type="secondary"
                        className="cancel-button"
                        icon={<CloseOutlined style={{ color: "red" }} />}
                        onClick={() => {
                          setSelected(null);
                          updateForm.resetFields();
                        }}
                        size={30}
                      />
                    </div>
                    <div className="add-agency-title">Update Agency</div>
                    <Form
                      form={updateForm}
                      onFinish={updateAgency}
                      layout="vertical"
                      requiredMark={false}
                      disabled={updateButtonLoading}
                    >
                      <Form.Item
                        name="agencyName"
                        rules={[
                          {
                            required: true,
                            message: "Enter Agency Name !!",
                          },
                        ]}
                      >
                        <Input
                          className="login-input"
                          id="agencyName1"
                          placeholder="*Enter Agency Name"
                        />
                      </Form.Item>
                      <Form.Item name="agencyLogoPath">
                        <Input
                          className="login-input"
                          id="agencyLogoPath1"
                          placeholder="Enter Agency Logo url"
                        />
                      </Form.Item>
                      <Form.Item style={{ marginBottom: 0 }}>
                        <Button
                          type="secondary"
                          htmlType="submit"
                          className="user-profile login-button"
                          icon={<SaveOutlined />}
                          size={30}
                          style={{
                            border: "1px solid #8c8c8c",
                            marginTop: "5px",
                          }}
                          loading={updateButtonLoading}
                        >
                          Update{" "}
                        </Button>
                      </Form.Item>
                    </Form>
                  </>
                ) : (
                  <>
                    <div className="agency-update-logo">
                      <Button
                        type="secondary"
                        className="user-profile"
                        icon={<EditTwoTone />}
                        onClick={() => {
                          setSelected(agency.agencyId);
                          updateForm.setFields([
                            {
                              name: "agencyName",
                              value: agency.agencyName,
                            },
                            {
                              name: "agencyLogoPath",
                              value: agency.agencyLogoPath,
                            },
                          ]);
                        }}
                        size={30}
                      />
                    </div>
                    <div className="agency-logo">
                      {agency.agencyLogoPath ? (
                        <img
                          src={agency.agencyLogoPath}
                          alt={agency.agencyName}
                        />
                      ) : (
                        <Skeleton.Image />
                      )}
                    </div>
                    <div className="agency-name">{agency.agencyName}</div>
                  </>
                )}
              </Card>
            );
          })}
        </div>
      )}
    </div>
  );
}

export default Agency;
