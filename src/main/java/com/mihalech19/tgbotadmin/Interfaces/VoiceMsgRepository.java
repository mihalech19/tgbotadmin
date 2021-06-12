package com.mihalech19.tgbotadmin.Interfaces;

import com.mihalech19.tgbotadmin.Entities.VoiceMsg;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VoiceMsgRepository extends PagingAndSortingRepository<VoiceMsg, String> {
    
}
