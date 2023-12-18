package com.projectBackend.project.service;


import com.projectBackend.project.dto.MusicDTO;
import com.projectBackend.project.dto.UserReqDto;
import com.projectBackend.project.dto.UserResDto;
import com.projectBackend.project.entity.Member;
import com.projectBackend.project.entity.Music;
import com.projectBackend.project.repository.MusicRepository;
import com.projectBackend.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MusicService {
    private final MusicRepository musicRepository;
    private final UserRepository userRepository;

    //음악 전체 조회
    public List<MusicDTO> getAllMusic() {
        List<Music> musics = musicRepository.findAll();
        List<MusicDTO> musicDTOS = new ArrayList<>();
        for (Music music : musics) {
            musicDTOS.add(convertEntityToDto(music));
        }
        return musicDTOS;
    }

    //상세 조회
    public MusicDTO getMusicById(Long id) {
        Optional<Music> musicOptional = musicRepository.findById(id);
        if (musicOptional.isPresent()) {
            Music music = musicOptional.get();
            return convertEntityToDto(music);
        } else {
            return null;
        }
    }

    //음악 검색
    public List<MusicDTO> searchMusic(String keyword) {
        List<Music> foundMusics = musicRepository.findByMusicTitleContainingIgnoreCase(keyword);
        List<MusicDTO> musicDTOS = new ArrayList<>();
        for (Music music : foundMusics) {
            musicDTOS.add(convertEntityToDto(music));
        }
        return musicDTOS;
    }


    //음악 삭제
    public boolean deleteMusic(Long id) {
        Optional<Music> musicOptional = musicRepository.findById(id);
        if (musicOptional.isPresent()) {
            musicRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    //음악 수정
    public boolean modifyMusic(Long id, MusicDTO musicDTO) {
        try {
            Music music = musicRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("해당 음원이 존재하지 않습니다.")
            );

//            Member member = UserRepository.findByUserNickname(musicDTO.getUserNickname()); // userNickname으로 Member를 찾음
//            music.setMember(member);
            Optional<Member> member = userRepository.findByUserNickname("nickname");

            music.setMusicTitle(musicDTO.getMusicTitle());
            music.setLyricist(musicDTO.getLyricist());
            music.setComposer(musicDTO.getComposer());
            music.setGenre(musicDTO.getGenre());
            music.setPurchaseCount(musicDTO.getPurchaseCount());
            music.setLyrics(musicDTO.getLyrics());
            music.setReleaseDate(musicDTO.getReleaseDate());
            music.setThumbnailImage(musicDTO.getThumbnailImage());
            music.setPromoImage(musicDTO.getPromoImage());
            music.setMusicInfo(musicDTO.getMusicInfo());
            musicRepository.save(music);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // 페이지네이션
    public List<MusicDTO> getMusicList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Music> musics = musicRepository.findAll(pageable).getContent();
        List<MusicDTO> musicDTOS = new ArrayList<>();
        for (Music music : musics) {
            musicDTOS.add(convertEntityToDto(music));
        }
        return musicDTOS;
    }

    // 페이지 수 조회
    public int getMusicPage(Pageable pageable) {
        return musicRepository.findAll(pageable).getTotalPages();
    }


    // 음악 등록을 위해 유저의 닉네임으로 객체를 반환하는 메서드
    public Member findUser (UserReqDto userReqDto) {
        try {
            System.out.println("findByNickName try!!!");
            String nickName = userReqDto.getUserNickname();
            Optional<Member> memberOptional = userRepository.findByUserNickname(nickName);
            System.out.println("memberOptional" + memberOptional);
            // memberOptional가 비어있지 않다면 해당 엔티티 객체 반환
            if (memberOptional.isPresent()) {
                System.out.println("member try!!!!!!!!!!!!!!");
                Member memberEntity = memberOptional.get();
                System.out.println("memberEntity");
                return memberEntity;
            } else {
                System.out.println("member not try!!!!!!!!!!!!!!");
                // 찾고자 하는 회원이 존재하지 않는 경우에 대한 처리
                // 예외를 던지거나, null을 반환하거나, 기타 적절한 처리를 수행할 수 있습니다.
                return null; // 예시로 null을 반환하는 방법입니다.
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("findByNickName not try!!!");
            return null;
        }
    }

    // 음악 등록
    public MusicDTO addMusic(MusicDTO musicDTO, UserReqDto userReqDto) {
        try {
            // 회원 객체 생성
            Member member = findUser(userReqDto);
            if (member == null) {
                // 회원을 찾을 수 없는 경우 예외 처리
                return null;
            }
            // 데이터 베이스에 음악 정보 저장
            Music music = new Music();
            music.setMusicTitle(musicDTO.getMusicTitle());
            music.setLyricist(musicDTO.getLyricist());
            music.setComposer(musicDTO.getComposer());
            music.setGenre(musicDTO.getGenre());
            music.setPurchaseCount(musicDTO.getPurchaseCount());
            music.setLyrics(musicDTO.getLyrics());
            music.setReleaseDate(musicDTO.getReleaseDate()); // releaseDate 파싱하여 설정
            music.setThumbnailImage(musicDTO.getThumbnailImage());
            music.setPromoImage(musicDTO.getPromoImage());
            music.setMusicInfo(musicDTO.getMusicInfo());
            music.setMember(member);
            System.out.println("member : " + member);
            System.out.println("music : " + music);
            System.out.println("nickName : " + music.getMember().getUserNickname());



            // 데이터 베이스에서 닉네임 정보를 가져와 DTO 로 전달
            MusicDTO returnDTO = new MusicDTO();
            returnDTO.setMusicTitle(music.getMusicTitle());
            returnDTO.setComposer(music.getComposer());
            returnDTO.setLyricist(music.getLyricist());
            returnDTO.setGenre(music.getGenre());
            returnDTO.setPurchaseCount(music.getPurchaseCount());
            returnDTO.setLyrics(music.getLyrics());
            returnDTO.setReleaseDate(music.getReleaseDate());
            returnDTO.setThumbnailImage(music.getThumbnailImage());
            returnDTO.setPromoImage(music.getPromoImage());
            returnDTO.setUserNickname(music.getMember().getUserNickname());
            musicRepository.save(music);
//        music.setMusicInfo(musicDTO.getMusicInfo());
            log.info("returnDto : {}", returnDTO);
            return returnDTO;
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("회원 정보 반환 실패");
            return null;
        }
    }



    // DTO를 객체로 변환
    private Music convertDtoToEntity(MusicDTO musicDTO) {
        Music music = new Music();
        music.setMusicTitle(musicDTO.getMusicTitle());
        music.setLyricist(musicDTO.getLyricist());
        music.setComposer(musicDTO.getComposer());
        music.setGenre(musicDTO.getGenre());
        music.setPurchaseCount(musicDTO.getPurchaseCount());
        music.setLyrics(musicDTO.getLyrics());
        music.setReleaseDate(musicDTO.getReleaseDate()); // releaseDate 파싱하여 설정
        music.setThumbnailImage(musicDTO.getThumbnailImage());
        music.setPromoImage(musicDTO.getPromoImage());
        music.setMusicInfo(musicDTO.getMusicInfo());
        return music;
    }

    // releaseDate를 LocalDate로 파싱하는 메서드 추가
    private LocalDate parseReleaseDate(String releaseDateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            return LocalDate.parse(releaseDateStr, formatter); // 문자열을 LocalDate로 변환
        } catch (DateTimeParseException e) {
            // 날짜를 파싱하는 도중에 예외가 발생할 경우 처리할 내용
            e.printStackTrace();
            return null; // 유효하지 않은 경우 null 반환하거나 예외 처리에 따라 다른 방식으로 처리
        }
    }


    // 엔티티 객체를 DTO로 변환
    private MusicDTO convertEntityToDto(Music music) {
        MusicDTO musicDTO = new MusicDTO();
        musicDTO.setMusicTitle(music.getMusicTitle());
        musicDTO.setComposer(music.getComposer());
        musicDTO.setLyricist(music.getLyricist());
        musicDTO.setGenre(music.getGenre());
        musicDTO.setPurchaseCount(music.getPurchaseCount());
        musicDTO.setLyrics(music.getLyrics());
        musicDTO.setReleaseDate(music.getReleaseDate());
        musicDTO.setThumbnailImage(music.getThumbnailImage());
        musicDTO.setPromoImage(music.getPromoImage());
        music.setMusicInfo(musicDTO.getMusicInfo());
        return musicDTO;
    }
}
