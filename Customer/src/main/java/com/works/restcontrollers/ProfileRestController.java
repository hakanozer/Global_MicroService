package com.works.restcontrollers;

import com.works.profile.IProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileRestController {

    final IProfile iProfile;

    @GetMapping("/profile")
    public ResponseEntity profile() {
        return new ResponseEntity( iProfile.config(), HttpStatus.OK );
    }

}
