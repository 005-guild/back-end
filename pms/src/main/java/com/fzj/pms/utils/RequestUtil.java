package com.fzj.pms.utils;

import com.fzj.pms.entity.parms.BoardParm;
import com.fzj.pms.entity.parms.OpinionParm;
import com.fzj.pms.entity.parms.ParkParm;
import com.fzj.pms.entity.parms.RepairsParm;
import com.fzj.pms.entity.pms.Board;
import com.fzj.pms.entity.pms.Opinion;
import com.fzj.pms.entity.pms.Park;
import com.fzj.pms.entity.pms.Repairs;
import com.fzj.pms.service.UserService;
import com.fzj.pms.service.impl.UserServiceImpl;
import org.apache.commons.lang3.StringUtils;

public class RequestUtil {

    public static Opinion ParmToEntity(OpinionParm opinionParm){
        Opinion opinion = new Opinion();
        opinion.setOpinionTitle(opinionParm.getOpinionTitle());
        opinion.setReplyStatus(opinionParm.getReplyStatus());
        opinion.setOpinionContent(opinionParm.getOpinionContent());
        opinion.setReplyDate(opinionParm.getReplyDate());
        opinion.setReplyContent(opinionParm.getReplyContent());
        opinion.setReplyId(opinionParm.getReplyId());
        opinion.setCreateTime(opinionParm.getCreateTime());
        opinion.setCurrentPage(opinionParm.getCurrentPage());
        opinion.setId(opinionParm.getId());
        opinion.setDeleteFlag(opinionParm.getDeleteFlag());
        opinion.setPageSize(opinionParm.getPageSize());
        opinion.setUpdateTime(opinionParm.getUpdateTime());
        return opinion;
    }

    public static Repairs ParmToEntity(RepairsParm repairsParm){
        Repairs repairs = new Repairs();
        repairs.setRemark(repairsParm.getRemark());
        repairs.setRepairsStatus(repairsParm.getRepairsStatus());
        repairs.setLinkman(repairsParm.getLinkman());
        repairs.setLinkPhone(repairsParm.getLinkPhone());
        repairs.setLinkAddress(repairsParm.getLinkAddress());
        repairs.setFinishDate(repairsParm.getFinishDate());
        repairs.setId(repairsParm.getId());
        repairs.setDeleteFlag(repairsParm.getDeleteFlag());
        repairs.setPageSize(repairsParm.getPageSize());
        repairs.setCurrentPage(repairsParm.getCurrentPage());
        return repairs;
    }

    public static Park ParmToEntity(ParkParm parkParm){
        Park park = new Park();
        park.setUseStatus(parkParm.getUseStatus());
        park.setParkType(parkParm.getParkType());
        park.setExpireDate(park.getExpireDate());
        park.setPosition(parkParm.getPosition());
        park.setCurrentPage(parkParm.getCurrentPage());
        park.setPageSize(parkParm.getPageSize());
        park.setId(parkParm.getId());
        return park;
    }

    public static Board ParmToEntity(BoardParm boardParm){
        Board board = new Board();
        board.setBoardContent(boardParm.getBoardContent());
        board.setLinkName(boardParm.getLinkName());
        board.setLinkPhone(boardParm.getLinkPhone());
        board.setTitle(boardParm.getTitle());
        board.setUseStatus(boardParm.getUseStatus());
        board.setId(boardParm.getId());
        board.setCurrentPage(boardParm.getCurrentPage());
        board.setPageSize(boardParm.getPageSize());
        return board;
    }
}
