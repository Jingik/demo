import React, { useState } from 'react';
import './Notifications.css';
import Sidebar from '../components/ui/Sidebar'

const Notifications = () => {
  const [activeTab, setActiveTab] = useState('sent');

  const sentNotifications = [
    "보낸 알림 1",
    "보낸 알림 2",
    "보낸 알림 3"
  ];

  const receivedNotifications = [
    "받은 알림 1",
    "받은 알림 2",
    "받은 알림 3"
  ];

  return (
    <>
    <div className="notifications-container">
      <h1>알림함</h1>
      <div className="tabs">
        <button onClick={() => setActiveTab('sent')} className={activeTab === 'sent' ? 'active' : ''}>
          보낸 알림함
        </button>
        <button onClick={() => setActiveTab('received')} className={activeTab === 'received' ? 'active' : ''}>
          받은 알림함
        </button>
      </div>
      <div className="notifications-list">
        {activeTab === 'sent' ? (
          sentNotifications.map((notification, index) => (
            <div key={index} className="notification-item">
              {notification}
            </div>
          ))
        ) : (
          receivedNotifications.map((notification, index) => (
            <div key={index} className="notification-item">
              {notification}
            </div>
          ))
        )}
      </div>
    </div>
    <Sidebar />
    </>
  );
};

export default Notifications;
