import axiosInstance from './AxiosInstance';

// 경력 추가
export const createEmployment = (companyName,position,department,startDate,endDate,achievement) => {
  return axiosInstance.post('/api/employment',{
    companyName,
    position,
    department,
    startDate,
    endDate,
    achievement
  }) 
  .then (response => response.data)
  .catch( error => {
    throw error

  })
}

// 경력 수정
export const updateEmployment = (employmentId,updateParam) => {
  return axiosInstance.put(`/api/employment/${employmentId}`,updateParam)
  .then (response => response.data)
  .catch (error => {
    throw error
  })
}

// 경력 조회
export const getEmployment = (employmentId) => {
  return axiosInstance.get(`/api/employment/${employmentId}`)
  .then (response => response.data)
  .catch (error => {
    throw error
  })
}

// 경력 삭제
export const deleteEmployment = (employmentId) => {
  return axiosInstance.delete(`/api/employment/${employmentId}`)
  .then(response => response.data)
  .catch(error=>{
    throw error
  })
}


// 학력 추가
export const createEducation = (universityName,degree,major,startDate,graduateDate) => {
  return axiosInstance.post('/api/education',{
    universityName,
    degree,
    major,
    startDate,
    graduateDate
  }) 
  .then (response => response.data)
  .catch( error => {
    throw error

  })
}

// 학력 수정
export const updateEducation = (educationId,updateParam) => {
  return axiosInstance.put(`/api/education/${educationId}`,updateParam)
  .then (response => response.data)
  .catch (error => {
    throw error
  })
}

// 학력 조회
export const getEducation = (educationId) => {
  return axiosInstance.get(`/api/education/${educationId}`)
  .then (response => response.data)
  .catch (error => {
    throw error
  })
}

// 학력 삭제
export const deleteEducation = (educationId) => {
  return axiosInstance.delete(`/api/education/${educationId}`)
  .then(response => response.data)
  .catch(error=>{
    throw error
  })
}