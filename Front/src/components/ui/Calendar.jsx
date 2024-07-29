import { useRef, useState, useEffect } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin, { Draggable } from '@fullcalendar/interaction';
import './Modal.css'; // 모달 스타일 추가
import './Calendar.css'; // 캘린더 스타일 추가

// Calendar 컴포넌트 정의
export default function Calendar() {
  const calendarRef = useRef(null); // 캘린더 참조를 위한 useRef 사용
  const [events, setEvents] = useState([
    { id: '1', title: 'event 1', date: '2019-04-01' },
    { id: '2', title: 'event 2', date: '2019-04-02' }
  ]); // 초기 이벤트 상태 설정
  const [newEvent, setNewEvent] = useState({ id: '', title: '', date: '' }); // 새로운 이벤트 상태 설정
  const [showModal, setShowModal] = useState(false); // 모달 표시 여부 상태 설정

  // 컴포넌트가 마운트될 때 호출되는 useEffect
  useEffect(() => {
    let draggableEl = document.getElementById('external-events');
    new Draggable(draggableEl, {
      itemSelector: '.fc-event',
      eventData: function(eventEl) {
        let title = eventEl.getAttribute('title');
        let id = eventEl.getAttribute('data');
        return {
          title: title,
          id: id
        };
      }
    });
  }, []);

  // 날짜를 클릭했을 때 호출되는 함수
  const handleDateClick = (arg) => {
    setNewEvent({ id: Date.now().toString(), title: '', date: arg.dateStr });
    setShowModal(true); // 모달 표시
  };

  // 이벤트를 받았을 때 호출되는 함수
  const handleEventReceive = (info) => {
    const newEvent = {
      id: Date.now().toString(),
      title: info.event.title,
      date: info.event.startStr
    };
    setEvents(prevEvents => [...prevEvents, newEvent]);
  };

  // 입력 필드가 변경될 때 호출되는 함수
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewEvent({
      ...newEvent,
      [name]: value
    });
  };

  // 새로운 이벤트를 추가하는 함수
  const addEvent = () => {
    setEvents(prevEvents => [...prevEvents, newEvent]);
    setNewEvent({ id: '', title: '', date: '' });  // 폼 초기화
    setShowModal(false);  // 모달 숨기기
  };

  // 엔터 키를 눌렀을 때 호출되는 함수
  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      addEvent();
    }
  };

  // 이벤트를 삭제하는 함수
  const deleteEvent = (eventId) => {
    setEvents(events.filter(event => event.id !== eventId));
  };

  // 모달을 닫는 함수
  const closeModal = () => {
    setShowModal(false);
  };

  return (
    <>
      <div id="external-events"></div>
      <div className="calendar-container">
        <FullCalendar
          ref={calendarRef}
          plugins={[dayGridPlugin, interactionPlugin]}
          dateClick={handleDateClick} // 날짜 클릭 이벤트 핸들러
          eventReceive={handleEventReceive} // 이벤트 수신 핸들러
          eventContent={renderEventContent(deleteEvent)} // 이벤트 콘텐츠 렌더링
          initialView="dayGridMonth" // 초기 뷰 설정
          weekends={true} // 주말 표시 여부
          editable={true} // 이벤트 수정 가능 여부
          droppable={true} // 드래그 앤 드롭 가능 여부
          events={events} // 이벤트 데이터
          height="auto" // 캘린더 높이 자동 조정
        />
      </div>

      {showModal && (
        <div className="modal-overlay" onClick={closeModal}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <div className="modal-header">Add Event</div>
            <div className="modal-body">
              <input
                type="text"
                name="title"
                value={newEvent.title}
                onChange={handleInputChange}
                onKeyPress={handleKeyPress} // 엔터 키 이벤트 핸들러 추가
                placeholder="Event Title"
              />
              <input
                type="date"
                name="date"
                value={newEvent.date}
                onChange={handleInputChange}
                onKeyPress={handleKeyPress} // 엔터 키 이벤트 핸들러 추가
              />
            </div>
            <div className="modal-footer">
              <button onClick={addEvent}>Add Event</button>
              <button onClick={closeModal}>Cancel</button>
            </div>
          </div>
        </div>
      )}
    </>
  );
}

// 이벤트 콘텐츠를 렌더링하는 함수
function renderEventContent(deleteEvent) {
  const EventContent = (eventInfo) => {
    return (
      <div className="fullcalendar-event">
        <b>{eventInfo.timeText}</b>
        <i>{eventInfo.event.title}</i>
        <button onClick={() => deleteEvent(eventInfo.event.id)}>Delete</button>
      </div>
    );
  };

  EventContent.displayName = 'EventContent';
  return EventContent;
}
